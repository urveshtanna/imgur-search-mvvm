package com.urveshtanna.imgur.data.local.db.dao

import com.urveshtanna.imgur.data.model.Comment
import io.reactivex.Observable

interface DBHelper {

    fun insertComment(comment: Comment): Observable<Boolean>

    fun getAllCommentByGalleryId(galleryId: String) : Observable<List<Comment>>

}