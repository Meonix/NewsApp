package com.mionix.newsapp.api

import com.mionix.newsapp.model.ListPopularNews
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiWebService {
    @GET("top-headlines?country=us")
    suspend fun getPoppularNews(@Query("page") page: Int): ListPopularNews
}