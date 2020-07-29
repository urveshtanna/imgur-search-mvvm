package com.urveshtanna.imgur.ui.main.navigator

import com.urveshtanna.imgur.data.model.GalleryData

interface MainSearchNavigator {

    fun handleError(throwable: Throwable)

    fun loadData(galleryData: List<GalleryData>)

    fun openImageDetailsScreen()
}