package com.mionix.newsapp.repo

import com.mionix.newsapp.api.ApiWebService
import com.mionix.newsapp.base.Response
import com.mionix.newsapp.base.ResponseError
import com.mionix.newsapp.model.ListPopularNews

class PopularNewsRepo(private val mApi: ApiWebService) {
    suspend fun getPopularNews(page :Int,country:String): Response<ListPopularNews> {
        return try {
            Response.success(mApi.getPopularNews(page,country))
        } catch (ex:Exception) {
            Response.error(ResponseError(101,ex.message.toString()))
        }
    }
}