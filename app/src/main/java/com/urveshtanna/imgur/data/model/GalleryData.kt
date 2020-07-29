package com.urveshtanna.imgur.data.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName


data class GalleryData(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("images")
    val images: List<ImageData> = ArrayList()
) {

    fun getImageUrl(): String? {
        if (images.size > 0 && images.get(0).link != null) {
            return images.get(0).link
        } else {
            return null
        }
    }

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