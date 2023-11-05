package com.example.everything.net

import com.example.everything.data.SearchResponse
import com.example.everything.net.RetrofitClient.USER_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EverythingApi {

    @GET("everything")
    suspend fun search(
        @Query("q") query: String,
        @Query("page") pageNumber: Int,
        @Query("apiKey") apiKey: String = USER_KEY
    ): SearchResponse?
}