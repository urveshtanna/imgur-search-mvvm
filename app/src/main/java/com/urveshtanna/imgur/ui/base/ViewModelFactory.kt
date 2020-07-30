package com.urveshtanna.imgur.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.urveshtanna.imgur.data.local.DataManager
import com.urveshtanna.imgur.data.local.db.AppDBHelper
import com.urveshtanna.imgur.data.local.db.AppDatabase
import com.urveshtanna.imgur.data.remote.APIHelper
import com.urveshtanna.imgur.data.repository.SearchGalleryRepository
import com.urveshtanna.imgur.ui.comment.viewmodel.CommentSectionViewModel
import com.urveshtanna.imgur.ui.main.viewmodel.MainSearchViewModel

class ViewModelFactory(private val apiHelper: APIHelper, private val dataManager: DataManager): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainSearchViewModel::class.java)){
            return MainSearchViewModel(SearchGalleryRepository(apiHelper), dataManager) as T
        }else if(modelClass.isAssignableFrom(CommentSectionViewModel::class.java)){
            return CommentSectionViewModel(dataManager) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}