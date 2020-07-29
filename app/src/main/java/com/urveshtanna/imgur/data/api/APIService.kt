package com.urveshtanna.imgur.data.api

import com.urveshtanna.imgur.data.model.SearchResponse
import io.reactivex.Single

interface APIService {

    fun getSearchResult(query: String): Single<SearchResponse>

}