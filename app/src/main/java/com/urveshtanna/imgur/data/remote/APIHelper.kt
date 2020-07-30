package com.urveshtanna.imgur.data.remote

class APIHelper(private val apiService: APIService) {

    fun getSearchResult(query: String?, accessToken: String?) =
        apiService.getSearchResult(query, accessToken);

}