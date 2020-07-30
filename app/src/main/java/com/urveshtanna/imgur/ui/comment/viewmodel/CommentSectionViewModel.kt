package com.urveshtanna.imgur.ui.comment.viewmodel

import com.urveshtanna.imgur.data.local.DataManager
import com.urveshtanna.imgur.data.local.db.AppDBHelper
import com.urveshtanna.imgur.data.model.Comment
import com.urveshtanna.imgur.ui.base.BaseViewModel
import com.urveshtanna.imgur.ui.comment.navigator.CommentSectionNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class CommentSectionViewModel(dataManager: DataManager) :
    BaseViewModel<CommentSectionNavigator>() {

    val appDBHelper = AppDBHelper(dataManager.appDatabase)

    fun getComments(galleryId: String) {
        setIsLoading(true)
        compositeDisposable?.add(
            appDBHelper.getAllCommentByGalleryId(galleryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setIsLoading(false)
                    getNavigator()?.loadComments(it)
                }, {
                    setIsLoading(false)
                    getNavigator()?.handleError(it)
                })
        )
    }

    fun isValidComment(username: String?, comment: String?): Boolean {
        if (comment.isNullOrEmpty()) {
            return false
        } else if (username.isNullOrEmpty()) {
            return false
        } else {
            return true
        }
    }

    fun addComment(username: String?, commentValue: String, galleryId: String?) {
        val comment = Comment(commentValue, username, Date(), galleryId)
        setIsLoading(true)
        compositeDisposable?.add(
            appDBHelper.insertComment(comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setIsLoading(false)
                    getNavigator()?.newCommentAdded(comment)
                }, {
                    setIsLoading(false)
                    getNavigator()?.handleError(it)
                })
        )
    }


}