package com.urveshtanna.imgur.data.remote

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.rx2androidnetworking.Rx2AndroidNetworking
import com.urveshtanna.imgur.data.model.SearchResponse
import io.reactivex.Single

class APIServiceImpl : APIService {

    private val BASE_URL = "https://api.imgur.com/3"

    override fun getSearchResult(query: String?): Single<SearchResponse> {
        AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BASIC)
        return Rx2AndroidNetworking.get("$BASE_URL/gallery/search/1?q=$query")
            .addHeaders("Authorization", "Client-ID 137cda6b5008a7c")
            .build()
            .getObjectSingle(SearchResponse::class.java)
    }
}