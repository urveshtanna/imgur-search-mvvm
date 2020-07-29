package com.urveshtanna.imgur.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.urveshtanna.imgur.data.local.db.AppDBHelper
import com.urveshtanna.imgur.data.local.db.AppDatabase
import com.urveshtanna.imgur.data.remote.APIHelper
import com.urveshtanna.imgur.data.repository.SearchGalleryRepository
import com.urveshtanna.imgur.ui.comment.viewmodel.CommentSectionViewModel
import com.urveshtanna.imgur.ui.main.viewmodel.MainSearchViewModel

class ViewModelFactory(private val apiHelper: APIHelper, private val appDatabase: AppDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainSearchViewModel::class.java)){
            return MainSearchViewModel(SearchGalleryRepository(apiHelper)) as T
        }else if(modelClass.isAssignableFrom(CommentSectionViewModel::class.java)){
            return CommentSectionViewModel(AppDBHelper(appDatabase)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}