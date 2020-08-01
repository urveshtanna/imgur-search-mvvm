package com.urveshtanna.imgur.ui.main.viewmodel

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.jakewharton.rxrelay3.PublishRelay
import com.urveshtanna.imgur.data.local.DataManager
import com.urveshtanna.imgur.data.repository.SearchGalleryRepository
import com.urveshtanna.imgur.ui.base.BaseViewModel
import com.urveshtanna.imgur.ui.main.navigator.MainSearchNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainSearchViewModel(
    private val searchGalleryRepository: SearchGalleryRepository,
    var dataManager: DataManager
) :
    BaseViewModel<MainSearchNavigator>() {

    var page: Int = 1
    var isRecyclerViewLoading: Boolean = false
    var hasMoreSearchResultData: Boolean = true

    fun getGalleryFromSearchQuery(query: String?) {
        if (page <= 1) {
            setIsLoading(true)
        }
        compositeDisposable?.add(
            searchGalleryRepository
                .getSearchResult(query, dataManager.appDataSharePrefImpl.getClientToken(), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    isRecyclerViewLoading = false
                    if (it.data.size == 0) {
                        hasMoreSearchResultData = false;
                    }

                    if (page <= 1) {
                        page++
                        setIsLoading(false)
                        getNavigator()?.loadNewData(it.data)
                    } else {
                        page++
                        getNavigator()?.loadData(it.data)
                    }
                    compositeDisposable.clear()
                }, {
                    setIsLoading(false)
                    getNavigator()?.handleError(it)
                })
        )
    }

    fun resetPaginationValues() {
        page = 1;
        hasMoreSearchResultData = true
    }
}