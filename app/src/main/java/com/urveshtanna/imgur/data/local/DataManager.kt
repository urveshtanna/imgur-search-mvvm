package com.urveshtanna.imgur.data.local

import android.content.Context
import com.urveshtanna.imgur.data.local.db.AppDatabase
import com.urveshtanna.imgur.data.local.sharedPrefs.AppDataSharePrefImpl

class DataManager(context: Context) {

    var appDatabase: AppDatabase = AppDatabase.getInstance(context)!!
    var appDataSharePrefImpl: AppDataSharePrefImpl = AppDataSharePrefImpl(context)

}