package com.urveshtanna.imgur.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.urveshtanna.imgur.R
import com.urveshtanna.imgur.data.model.GalleryData
import com.urveshtanna.imgur.databinding.ItemGalleryDataBinding

class MainSearchResultAdapter(private val galleryData: ArrayList<GalleryData>,
                              private val listener: (GalleryData, View) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemGalleryDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_gallery_data, parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return galleryData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DataViewHolder) {
            holder.binding.model = galleryData.get(holder.adapterPosition)
            holder.binding.root.setOnClickListener {
                listener(galleryData.get(holder.adapterPosition), holder.binding.imgPreview)
            }
        }
    }

    class DataViewHolder(val binding: ItemGalleryDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    fun addData(newGalleryData: List<GalleryData>){
        galleryData.addAll(newGalleryData)
    }

    fun addNewData(newGalleryData: List<GalleryData>){
        galleryData.clear()
        galleryData.addAll(newGalleryData)
    }

}