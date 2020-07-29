package com.urveshtanna.imgur.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("data")
    val data: List<GalleryData> = ArrayList(),
    @SerializedName("success")
    val boolean: Boolean = false,
    @SerializedName("status")
    val status: Int = 0
) {
}