package com.medium.binding.src.join.join2

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.medium.binding.R
import com.medium.binding.config.BaseFragment
import com.medium.binding.databinding.FragmentJoin2Binding
import com.medium.binding.src.join.JoinActivity

class Join2Fragment: BaseFragment<FragmentJoin2Binding>(
    FragmentJoin2Binding::bind,
    R.layout.fragment_join_2
) {

    // 숫자, 문자, 특수문자 3가지중 최소 2가지는 포함하도록, 8~20자
    // private val pwdRegExp = "^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#\$%^&*])(?=.*[0-9!@#\$%^&*]).{8,20}$"
    // 비밀번호, 비밀번호 확인 문자열
    private lateinit var pwdString: String
    private lateinit var pwdChkString: String
    // 전달받은 이메일 주소
    private var email: String? = "temp_jslkap@naver.com"

    // 다음 버튼 클릭 -> 포커싱 해제될 때 실행되는 코드를 막기 위한 플래그
    private var isHere = true


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isHere = true   // 현 프래그먼트(화면)에서 작업중이다

        // 전달 받은 이메일 주소
        email = arguments?.getString("email", "temp_jslkap@naver.com")

        // 비밀번호 오류 메시지를 감춘다
        binding.join2WrongPw.visibility = View.INVISIBLE

        // 첫번째 text칸 엔터키 -> 아래 editText로 이동
        binding.join2Password.setOnKeyListener { v, keyCode, event ->
            if(event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                binding.join2PasswordCheck.performClick()
            }
            false
        }

        // 2번째 editText 엔터버튼 클릭 -> 다음 버튼 자동 눌림
        binding.join2PasswordCheck.setOnKeyListener { v, keyCode, event ->
            if(event.action == KeyEvent.ACTION_DOWN &&
                (keyCode == KeyEvent.KEYCODE_ENDCALL || keyCode == KeyEvent.KEYCODE_ENTER)
            ){
                binding.join2Next.performClick()
            }
            false
        }

        // 다음 버튼 클릭 -> 비밀번호가 맞으면 닉네임 입력으로 넘어간다
        binding.join2Next.setOnClickListener(onClickNext)

        // 뒤로가기 버튼 -> 이메일 입력화면(join1) 돌아간다
        binding.join2Back.setOnClickListener{
            (activity!! as JoinActivity).onBackPressed()
        }

        // 각 입력칸이 포커싱되면 밑줄이 굵어진다
        binding.join2Password.onFocusChangeListener = onFocusPwd
        binding.join2PasswordCheck.onFocusChangeListener = onFocusPwdChk

        // 비밀번호 입력할 때 형식 검사
        // binding.join2Password.addTextChangedListener(pwdWatcher)
        // binding.join2PasswordCheck.addTextChangedListener(pwdChkWatcher)
    }

    // 비밀번호 입력 칸 포커스 -> 밑줄이 굵어진다
    private val onFocusPwd = View.OnFocusChangeListener { v, hasFocus ->
        when(hasFocus){
            true -> {
                binding.join2PwLineBold.visibility = View.VISIBLE
                binding.join2PwChkLineBold.visibility = View.INVISIBLE
            }
        }
    }

    // 비밀번호 확인 입력 칸 포커스 -> 밑줄이 굵어진다
    private val onFocusPwdChk = View.OnFocusChangeListener { v, hasFocus ->
        when(hasFocus){
            true -> {
                binding.join2PwChkLineBold.visibility = View.VISIBLE
                binding.join2PwLineBold.visibility = View.INVISIBLE
            }
        }
    }

    // 다음 버튼 클릭
    private val onClickNext = View.OnClickListener {
        pwdString = binding.join2Password.text.toString()
        pwdChkString = binding.join2PasswordCheck.text.toString()

        /*// 비밀번호 형식이 올바르지 않을 때
        if(!isValidPwd(pwdString)){
            Log.d("로그", "비밀번호 올바르지 않음")
            binding.join2WrongPw.let {
                it.text = String.format("비밀번호 형식이 맞지 않습니다")
                it.visibility = View.VISIBLE
            }
        }*/

        // 비밀번호가 8-20자가 아닐때
        if(pwdString.length < 8 || pwdString.length > 20) {
            binding.join2WrongPw.let {
                it.text = String.format("비밀번호 형식이 맞지 않습니다")
                it.visibility = View.VISIBLE
            }
        }
        // 비밀번호와 비밀번호 확인이 일치하지 않을 때
        else if(pwdString != pwdChkString) {
            binding.join2WrongPw.let {
                it.text = String.format("비밀번호가 맞지 않습니다")
                it.visibility = View.VISIBLE
            }
        }
        // 형식이 올바르고, 일치하면 다음 화면으로 넘어간다
        else{
            email?.let { email ->
                (activity!! as JoinActivity).moveToJoin3(email, pwdString, pwdChkString)
            }
        }
    }

    // 정규식으로 비밀번호 형식 검사
    /*fun isValidPwd(pwd: String):Boolean {
        var returnValue = false

        try
        {
            val p = Pattern.compile(pwdRegExp)
            val m = p.matcher(pwd)

            if (m.matches()) {
                returnValue = true
            }

            return returnValue
        }
        catch (e: Exception) {
            return false
        }
    }*/


    // 비밀번호 입력 중 실시간으로 형식 검사 하는 코드
    /*private val pwdWatcher = object: TextWatcher{
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            when(isValidPwd(s.toString())){

                // 비밀번호가 형식이 올바르지 않을 때
                false -> {
                    binding.join2WrongPw.text = String.format("비밀번호가 올바르지 않습니다")
                    binding.join2WrongPw.visibility = View.VISIBLE
                }

                true -> binding.join2WrongPw.visibility = View.INVISIBLE
            }
        }
    }*/

    /*private val pwdChkWatcher = object: TextWatcher{
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }*/

    override fun onDestroyView() {
        isHere = false
        super.onDestroyView()
    }
}