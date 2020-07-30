package com.urveshtanna.imgur.data.local.db

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.urveshtanna.imgur.data.local.db.convertors.DateConverter
import com.urveshtanna.imgur.data.local.db.dao.CommentDao
import com.urveshtanna.imgur.data.local.db.dao.DBHelper
import com.urveshtanna.imgur.data.model.Comment
import io.reactivex.Observable

@Database(entities = arrayOf(Comment::class), version = 1)
abstract class AppDatabase : RoomDatabase() {



    companion object {

        @JvmStatic
        var instance: AppDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "imgur_search_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }

    }

    abstract fun getCommentDao() : CommentDao

}