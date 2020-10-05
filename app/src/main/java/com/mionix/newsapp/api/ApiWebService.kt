package com.mionix.newsapp.api

import com.mionix.newsapp.model.ListPopularNews
import com.mionix.newsapp.model.Sources
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiWebService {
    @GET("top-headlines")
    suspend fun getPopularNews(
        @Query("page") page: Int,
        @Query("country") country: String
    ): ListPopularNews

    @GET("everything")
    suspend fun getSearchNews(
        @Query("q") keyWord: String,
        @Query("page") page: Int,
        @Query("language") language: String
    ): ListPopularNews
    @GET("sources")
    suspend fun getSourcesNews(
        @Query("country") country:String
    ): Sources
}