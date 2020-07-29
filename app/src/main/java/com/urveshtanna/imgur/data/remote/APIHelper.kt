package com.urveshtanna.imgur.data.remote

class APIHelper(private val apiService: APIService) {

    fun getSearchResult(query: String?) = apiService.getSearchResult(query);

}