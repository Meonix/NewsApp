package com.mionix.newsapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mionix.newsapp.base.BaseViewModel
import com.mionix.newsapp.base.onFailure
import com.mionix.newsapp.base.onLoading
import com.mionix.newsapp.base.onSuccess
import com.mionix.newsapp.model.ListPopularNews
import com.mionix.newsapp.model.Sources
import com.mionix.newsapp.repo.SearchNewsRepo
import com.mionix.newsapp.repo.SourcesNewsRepo

class SourcesNewsViewModel (private val mListSearchNews: SourcesNewsRepo) : BaseViewModel() {
    private val _getListSourcesNews = MutableLiveData<Sources>()
    val getListSourcesNews: LiveData<Sources> get() = _getListSourcesNews
    fun getListSourcesNews(country: String) = executeUseCase {
        mListSearchNews.getSourcesNews(country)
            .onLoading {
                println("Loading $it")
            }

            .onSuccess {
                _getListSourcesNews.value = it
            }

            .onFailure {
                println("err $it")
            }
    }
}