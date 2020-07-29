package com.urveshtanna.imgur.data.remote

import com.urveshtanna.imgur.data.model.SearchResponse
import io.reactivex.Single

interface APIService {

    fun getSearchResult(query: String?): Single<SearchResponse>

}