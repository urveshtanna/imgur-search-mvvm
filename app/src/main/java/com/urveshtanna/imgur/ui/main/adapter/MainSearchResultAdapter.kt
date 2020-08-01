package com.urveshtanna.imgur.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.urveshtanna.imgur.R
import com.urveshtanna.imgur.data.model.GalleryData
import com.urveshtanna.imgur.databinding.ItemGalleryDataBinding
import com.urveshtanna.imgur.databinding.ItemGalleryLoaderBinding

class MainSearchResultAdapter(
    private val galleryData: ArrayList<Any>,
    private val listener: (GalleryData, View) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_TYPE_GALLERY_DATA = 0
    val ITEM_TYPE_LOADER = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_TYPE_GALLERY_DATA) {
            val binding: ItemGalleryDataBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_gallery_data,
                parent,
                false
            )
            return DataViewHolder(binding)
        } else {
            val binding: ItemGalleryLoaderBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_gallery_loader,
                parent,
                false
            )
            return LoadingViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return galleryData.size
    }


    override fun getItemViewType(position: Int): Int {
        if (galleryData.get(position) is GalleryData) {
            return ITEM_TYPE_GALLERY_DATA
        } else if (galleryData.get(position) is String) {
            return ITEM_TYPE_LOADER
        }
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DataViewHolder && galleryData.get(holder.adapterPosition) is GalleryData) {
            holder.binding.model = galleryData.get(holder.adapterPosition) as GalleryData
            holder.binding.root.setOnClickListener {
                listener(
                    galleryData.get(holder.adapterPosition) as GalleryData,
                    holder.binding.imgPreview
                )
            }
        }
    }

    class DataViewHolder(val binding: ItemGalleryDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    class LoadingViewHolder(val binding: ItemGalleryLoaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    fun addData(newGalleryData: List<GalleryData>) {
        galleryData.removeAt(galleryData.size - 1)
        galleryData.addAll(newGalleryData)
        if (newGalleryData.size != 0) {
            addLoader()
        }
    }

    fun addNewData(newGalleryData: List<GalleryData>) {
        galleryData.clear()
        galleryData.addAll(newGalleryData)
        if (newGalleryData.size != 0) {
            addLoader()
        }
    }

    fun addLoader() {
        galleryData.add("Loading")
    }

}