package com.urveshtanna.imgur.data.remote

class APIHelper(private val apiService: APIService) {

    fun getSearchResult(query: String?, accessToken: String?, page: Int) =
        apiService.getSearchResult(query, accessToken, page);

}