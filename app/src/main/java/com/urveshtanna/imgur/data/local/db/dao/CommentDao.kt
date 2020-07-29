package com.urveshtanna.imgur.data.local.db.dao

import androidx.room.*
import com.urveshtanna.imgur.data.model.Comment
import io.reactivex.Single

@Dao
interface CommentDao {

    @Delete
    fun delete(comment: Comment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comment: Comment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<Comment>)

    @Query("SELECT * FROM comment_table WHERE galleryId LIKE :galleryId ORDER BY createdAt DESC")
    fun loadAllByGalleryId(galleryId: String): Single<List<Comment>>

}