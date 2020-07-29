package com.urveshtanna.imgur.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.urveshtanna.imgur.data.model.GalleryData
import com.urveshtanna.imgur.data.repository.SearchGalleryRepository
import com.urveshtanna.imgur.ui.base.BaseViewModel
import com.urveshtanna.imgur.ui.main.navigator.MainSearchNavigator
import com.urveshtanna.imgur.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainSearchViewModel(private val searchGalleryRepository: SearchGalleryRepository) :
    BaseViewModel<MainSearchNavigator>() {

    init {

    }

    fun getGalleryFromSearchQuery(query: String) {
        setIsLoading(true)
        compositeDisposable?.add(
            searchGalleryRepository
                .getSearchResult(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setIsLoading(false)
                    getNavigator()?.loadData(it.data)
                }, {
                    setIsLoading(false)
                    getNavigator()?.handleError(it)
                })
        )
    }

}