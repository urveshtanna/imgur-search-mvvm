package com.urveshtanna.imgur.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageData(
    @SerializedName("id")
    val id : String? = null,
    @SerializedName("title")
    val title : String? = null,
    @SerializedName("description")
    val description : String? = null,
    @SerializedName("type")
    val type : String? = null,
    @SerializedName("link")
    val link : String? = null
) : Parcelable