package com.urveshtanna.imgur.data.repository

import com.urveshtanna.imgur.data.api.APIHelper
import com.urveshtanna.imgur.data.model.SearchResponse
import io.reactivex.Single

class SearchGalleryRepository(private val apiHelper: APIHelper) {

    fun getSearchResult(query: String): Single<SearchResponse> {
        return apiHelper.getSearchResult(query)
    }

}