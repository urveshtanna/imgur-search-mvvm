package com.urveshtanna.imgur.ui.main.navigator

import android.view.View
import com.urveshtanna.imgur.data.model.GalleryData

interface MainSearchNavigator {

    fun handleError(throwable: Throwable)

    fun loadData(galleryData: List<GalleryData>)

    fun openImageDetailsScreen(galleryData: GalleryData, view: View)
}