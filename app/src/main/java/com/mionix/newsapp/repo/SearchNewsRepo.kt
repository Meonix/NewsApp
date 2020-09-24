package com.mionix.newsapp.repo

import com.mionix.newsapp.api.ApiWebService
import com.mionix.newsapp.base.Response
import com.mionix.newsapp.base.ResponseError
import com.mionix.newsapp.model.ListPopularNews

class SearchNewsRepo(private val mApi: ApiWebService) {
    suspend fun getSearchNews(
        keyWord: String,
        page: Int,
        language: String
    ): Response<ListPopularNews> {
        return try {
            Response.success(mApi.getSearchNews(keyWord, page, language))
        } catch (ex: Exception) {
            Response.error(ResponseError(101, ex.message.toString()))
        }
    }
}