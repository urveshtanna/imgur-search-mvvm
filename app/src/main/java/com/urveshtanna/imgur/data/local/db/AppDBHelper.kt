package com.urveshtanna.imgur.data.local.db

import com.urveshtanna.imgur.data.local.db.dao.DBHelper
import com.urveshtanna.imgur.data.model.Comment
import io.reactivex.Observable

class AppDBHelper(val appDatabase: AppDatabase): DBHelper {

    override fun insertComment(comment: Comment): Observable<Boolean> {
        return Observable.fromCallable {
            appDatabase.getCommentDao().insert(comment)
            true
        }
    }

    override fun getAllCommentByGalleryId(galleryId: String): Observable<List<Comment>> {
        return appDatabase.getCommentDao().loadAllByGalleryId(galleryId).toObservable()
    }


}