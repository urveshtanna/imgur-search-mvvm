package com.urveshtanna.imgur.ui.main.viewmodel

import com.jakewharton.rxrelay3.PublishRelay
import com.urveshtanna.imgur.data.repository.SearchGalleryRepository
import com.urveshtanna.imgur.ui.base.BaseViewModel
import com.urveshtanna.imgur.ui.main.navigator.MainSearchNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainSearchViewModel(private val searchGalleryRepository: SearchGalleryRepository) :
    BaseViewModel<MainSearchNavigator>() {

    init {

    }

    fun getGalleryFromSearchQuery(query: String?) {
        setIsLoading(true)
        compositeDisposable?.add(
            searchGalleryRepository
                .getSearchResult(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setIsLoading(false)
                    getNavigator()?.loadNewData(it.data)
                }, {
                    setIsLoading(false)
                    getNavigator()?.handleError(it)
                })
        )
    }

}