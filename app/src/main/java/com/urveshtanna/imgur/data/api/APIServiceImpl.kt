package com.urveshtanna.imgur.data.api

import com.rx2androidnetworking.Rx2AndroidNetworking
import com.urveshtanna.imgur.data.model.SearchResponse
import io.reactivex.Single
import java.util.*

class APIServiceImpl : APIService {

    private val BASE_URL = "https://api.imgur.com/3"

    override fun getSearchResult(query: String): Single<SearchResponse> {
        return Rx2AndroidNetworking.get("$BASE_URL/gallery/search/1?q=$query")
            .addHeaders("Authorization", "Client-ID 137cda6b5008a7c")
            .build()
            .getObjectSingle(SearchResponse::class.java)
    }
}