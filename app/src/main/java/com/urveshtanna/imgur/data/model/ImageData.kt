package com.urveshtanna.imgur.data.model

import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
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
) : Parcelable{

    companion object {

        @BindingAdapter("imageUrl")
        @JvmStatic
        fun loadImage(imageView: ImageView, imgUrl: String?) {
            if (imgUrl != null) {
                Glide.with(imageView.getContext()).load(imgUrl).into(imageView)
            }
        }

    }

}