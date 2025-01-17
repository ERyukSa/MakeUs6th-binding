package com.medium.binding.src.main.home.models

import com.google.gson.annotations.SerializedName

open class SearchResult (
    @SerializedName("bookIdx") val bookIdx: Int = 0,
    @SerializedName("bookImgUrl") val bookImgUrl: String? = null,
    @SerializedName("bookName") val bookName: String? = null,
    @SerializedName("authorName") val authorName: String? = null,
    @SerializedName("contentsCount") val contentsCount: String? = null
)