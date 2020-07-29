package com.urveshtanna.imgur.ui.gallerydetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.urveshtanna.imgur.R
import com.urveshtanna.imgur.data.model.ImageData
import com.urveshtanna.imgur.databinding.ItemGalleryImageBinding

class GalleryImageAdapter(val imageList: MutableList<ImageData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding : ItemGalleryImageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_gallery_image, parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is DataViewHolder){
            holder.binding.model = imageList.get(holder.adapterPosition)
        }
    }

    public class DataViewHolder(var binding: ItemGalleryImageBinding) : RecyclerView.ViewHolder(binding.root) {

    }


}