package com.mionix.newsapp.repo

import com.mionix.newsapp.api.ApiWebService
import com.mionix.newsapp.base.Response
import com.mionix.newsapp.base.ResponseError
import com.mionix.newsapp.model.ListPopularNews
import com.mionix.newsapp.model.Sources

class SourcesNewsRepo(private val mApi: ApiWebService) {
    suspend fun getSourcesNews(
        country: String
    ): Response<Sources> {
        return try {
            Response.success(mApi.getSourcesNews(country))
        } catch (ex: Exception) {
            Response.error(ResponseError(101, ex.message.toString()))
        }
    }
}