package com.urveshtanna.imgur.data.local.db.convertors

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        if(dateLong == null){
            return null
        }
        return Date(dateLong);
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        if(date == null){
            return null
        }
        return date.getTime();
    }
}