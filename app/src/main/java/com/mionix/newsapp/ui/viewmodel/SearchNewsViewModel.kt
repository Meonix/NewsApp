package com.mionix.newsapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mionix.newsapp.base.BaseViewModel
import com.mionix.newsapp.base.onFailure
import com.mionix.newsapp.base.onLoading
import com.mionix.newsapp.base.onSuccess
import com.mionix.newsapp.model.ListPopularNews
import com.mionix.newsapp.repo.PopularNewsRepo
import com.mionix.newsapp.repo.SearchNewsRepo

class SearchNewsViewModel(private val mListSearchNews: SearchNewsRepo) : BaseViewModel() {
    private val _getListSearchNews = MutableLiveData<ListPopularNews>()
    var isLoading = true
    val getListSearchNews: LiveData<ListPopularNews> get() = _getListSearchNews
    fun getListSearchNews(keyWord: String, page: Int, language: String) = executeUseCase {
        mListSearchNews.getSearchNews(keyWord, page, language)
            .onLoading {
                println("Loading $it")
            }

            .onSuccess {
                _getListSearchNews.value = it
            }

            .onFailure {
                println("err $it")
            }
    }
}