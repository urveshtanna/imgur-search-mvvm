package com.urveshtanna.imgur.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.urveshtanna.imgur.data.local.db.convertors.DateConverter
import java.util.*

@Entity(tableName = "comment_table")
@TypeConverters(DateConverter::class)
data class Comment(
    val value: String?,
    val username: String?,
    val createdAt: Date?,
    val galleryId: String?
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}