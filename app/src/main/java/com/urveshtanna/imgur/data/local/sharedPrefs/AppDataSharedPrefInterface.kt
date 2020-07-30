package com.urveshtanna.imgur.data.local.sharedPrefs

interface AppDataSharedPrefInterface {

    fun getClientToken() : String?

    fun setClientToken(clientToken: String)
}