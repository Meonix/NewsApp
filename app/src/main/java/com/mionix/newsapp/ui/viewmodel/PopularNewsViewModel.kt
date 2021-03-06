package com.mionix.newsapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mionix.newsapp.base.BaseViewModel
import com.mionix.newsapp.base.onFailure
import com.mionix.newsapp.base.onLoading
import com.mionix.newsapp.base.onSuccess
import com.mionix.newsapp.model.ListPopularNews
import com.mionix.newsapp.repo.PopularNewsRepo

class PopularNewsViewModel(private val mListPopularNews: PopularNewsRepo): BaseViewModel() {
    private val _getListPopularNews = MutableLiveData<ListPopularNews>()
    var isLoading = true
    val getListPopularNews: LiveData<ListPopularNews> get() = _getListPopularNews
    fun getListPopularNews(page: Int,country:String) = executeUseCase {
        mListPopularNews.getPopularNews(page,country)
            .onLoading {
                println("Loading $it")
            }

            .onSuccess {
                _getListPopularNews.postValue(it)
            }

            .onFailure {
                println("err $it")
            }
    }
}