package com.urveshtanna.imgur.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference


abstract class BaseViewModel<N>() :
    ViewModel() {
    val isLoading: ObservableBoolean? = ObservableBoolean()
    val compositeDisposable: CompositeDisposable?
    val mIsLoading = ObservableBoolean()
    var mNavigator: WeakReference<N>? = null

    override fun onCleared() {
        compositeDisposable!!.dispose()
        super.onCleared()
    }

    fun getIsLoading(): ObservableBoolean? {
        return mIsLoading
    }

    fun setIsLoading(isLoading: Boolean) {
        mIsLoading.set(isLoading)
    }

    init {
        compositeDisposable = CompositeDisposable()
    }

    open fun getNavigator(): N? {
        return mNavigator?.get()
    }

    open fun setNavigator(navigator: N) {
        this.mNavigator = WeakReference(navigator)
    }
}