package com.mionix.newsapp.repo

import com.mionix.newsapp.api.ApiWebService
import com.mionix.newsapp.base.Response
import com.mionix.newsapp.base.ResponseError
import com.mionix.newsapp.model.ListPopularNews

class PopularNewsRepo(private val mApi: ApiWebService) {
    suspend fun getPoppularNews(page :Int): Response<ListPopularNews> {
        return try {
            Response.success(mApi.getPoppularNews(page))
        } catch (ex:Exception) {
            Response.error(ResponseError(101,ex.message.toString()))
        }
    }
}