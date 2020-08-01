package com.urveshtanna.imgur.data.local.sharedPrefs

import android.content.Context
import android.content.SharedPreferences

class AppDataSharePrefImpl(context: Context) : AppDataSharedPrefInterface {

    private val PREF_KEY_CLIENT_TOKEN = "PREF_KEY_CLIENT_TOKEN"
    private val PREF_NAME = "imgur_search_pref"
    private var mPrefs: SharedPreferences

    init {
        mPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    override fun getClientToken(): String? {
        return mPrefs.getString(PREF_KEY_CLIENT_TOKEN, "")
    }

    override fun setClientToken(clientToken: String) {
        mPrefs.edit()?.putString(PREF_KEY_CLIENT_TOKEN, clientToken)?.apply()
    }
}