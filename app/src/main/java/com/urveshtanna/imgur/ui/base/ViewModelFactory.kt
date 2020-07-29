package com.urveshtanna.imgur.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.urveshtanna.imgur.data.api.APIHelper
import com.urveshtanna.imgur.data.repository.SearchGalleryRepository
import com.urveshtanna.imgur.ui.main.viewmodel.MainSearchViewModel

class ViewModelFactory(private val apiHelper: APIHelper): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainSearchViewModel::class.java)){
            return MainSearchViewModel(SearchGalleryRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}