package com.urveshtanna.imgur.ui.comment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.urveshtanna.imgur.R
import com.urveshtanna.imgur.data.model.Comment
import com.urveshtanna.imgur.databinding.ItemGalleryCommentBinding

class CommentAdapter(val comments: ArrayList<Comment>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemGalleryCommentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_gallery_comment,
            parent,
            false
        )
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DataViewHolder) {
            holder.binding.model = comments.get(holder.adapterPosition)
        }
    }

    class DataViewHolder(val binding: ItemGalleryCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    fun addData(newComments: List<Comment>) {
        comments.addAll(newComments)
    }

    fun addData(newComments: Comment) {
        comments.add(0, newComments)
    }

}