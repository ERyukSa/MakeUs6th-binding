package com.medium.binding.src.main.my_page.settings.profile

import com.medium.binding.config.BaseResponse
import com.medium.binding.src.main.my_page.settings.models.GetProfileResponse
import com.medium.binding.src.main.my_page.settings.models.PatchImgBody
import com.medium.binding.src.main.my_page.settings.models.PatchNicknameBody
import com.medium.binding.src.main.my_page.settings.models.PatchProfileBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH


interface SettingsProfileRetrofitInterface {

    // 유저 프로필 조회 API, jwt만 헤더값으로 넘겨주면 된다
    @GET("/users/profile")
    fun getProfile(): Call<GetProfileResponse>

    // 프로필 사진 변경 API
    @PATCH("/users/image")
    fun patchImg(@Body params: PatchImgBody): Call<BaseResponse>

    // 프로필 사진 삭제 API
    @DELETE("/users/image")
    fun deleteImg(): Call<BaseResponse>

    @PATCH("/users/nickname")
    fun patchNickname(@Body params: PatchNicknameBody): Call<BaseResponse>

    // 프로필 전체 변경 API
    @PATCH("/users/profile")
    fun patchProfile(@Body params: PatchProfileBody): Call<BaseResponse>
}