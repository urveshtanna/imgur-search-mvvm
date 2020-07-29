package com.urveshtanna.imgur.data.api

class APIHelper(private val apiService: APIService) {

    fun getSearchResult(query: String) = apiService.getSearchResult(query);

}