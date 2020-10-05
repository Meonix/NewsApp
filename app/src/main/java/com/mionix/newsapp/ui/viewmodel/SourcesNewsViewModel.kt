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
import com.mionix.newsapp.utils.AppConstants

class SourcesNewsViewModel (private val mListSearchNews: SourcesNewsRepo) : BaseViewModel() {
    var country = ""
    private val _getListSourcesNews = MutableLiveData<Sources>()
    val getListSourcesNews: LiveData<Sources> get() = _getListSourcesNews
    fun getDataByCountryOfItemSpinner(codeOfCountry:String){
       when (codeOfCountry){
           AppConstants.ARGENTINA -> {
               country = AppConstants.ISO_3166_1_ARGENTINA
           }
           AppConstants.UNITED_ARAB_EMIRATES -> {
               country = AppConstants.ISO_3166_1_UNITED_ARAB_EMIRATES
           }
           AppConstants.AUSTRIA -> {
               country = AppConstants.ISO_3166_1_AUSTRIA
           }
           AppConstants.AUSTRALIA -> {
               country = AppConstants.ISO_3166_1_AUSTRALIA
           }
           AppConstants.BELGIUM -> {
               country = AppConstants.ISO_3166_1_BELGIUM
           }
           AppConstants.BULGARIA -> {
               country = AppConstants.ISO_3166_1_BULGARIA
           }
           AppConstants.BRAZIL -> {
               country = AppConstants.ISO_3166_1_BRAZIL
           }
           AppConstants.CANADA -> {
               country = AppConstants.ISO_3166_1_CANADA
           }
           AppConstants.SWITZERLAND -> {
               country = AppConstants.ISO_3166_1_SWITZERLAND
           }
           AppConstants.CHINA -> {
               country = AppConstants.ISO_3166_1_CHINA
           }
           AppConstants.COLUMBIA -> {
               country = AppConstants.ISO_3166_1_COLUMBIA
           }
           AppConstants.CUBA -> {
               country = AppConstants.ISO_3166_1_CUBA
           }
           AppConstants.CZECHIA -> {
               country = AppConstants.ISO_3166_1_CZECHIA
           }
           AppConstants.GERMANY -> {
               country = AppConstants.ISO_3166_1_GERMANY
           }
           AppConstants.EGYPT -> {
               country = AppConstants.ISO_3166_1_EGYPT
           }
           AppConstants.FRANCE -> {
               country = AppConstants.ISO_3166_1_FRANCE
           }
           AppConstants.UK_OF_GB_AND_NI -> {
               country = AppConstants.ISO_3166_1_UK_OF_GB_AND_NI
           }
           AppConstants.GREECE -> {
               country = AppConstants.ISO_3166_1_GREECE
           }
           AppConstants.HONG_KONG -> {
               country = AppConstants.ISO_3166_1_HONG_KONG
           }
           AppConstants.HUNGARY -> {
               country = AppConstants.ISO_3166_1_HUNGARY
           }
           AppConstants.INDONESIA -> {
               country = AppConstants.ISO_3166_1_INDONESIA
           }
           AppConstants.IRELAND -> {
               country = AppConstants.ISO_3166_1_IRELAND
           }
           AppConstants.ISRAEL -> {
               country = AppConstants.ISO_3166_1_ISRAEL
           }
           AppConstants.INDIA -> {
               country = AppConstants.ISO_3166_1_INDIA
           }
           AppConstants.ITALY -> {
               country = AppConstants.ISO_3166_1_ITALY
           }
           AppConstants.JAPAN -> {
               country = AppConstants.ISO_3166_1_JAPAN
           }
           AppConstants.KOREA -> {
               country = AppConstants.ISO_3166_1_KOREA
           }
           AppConstants.LITHUANIA -> {
               country = AppConstants.ISO_3166_1_LITHUANIA
           }
           AppConstants.LATVIA -> {
               country = AppConstants.ISO_3166_1_LATVIA
           }
           AppConstants.MOROCCO -> {
               country = AppConstants.ISO_3166_1_MOROCCO
           }
           AppConstants.MEXICO -> {
               country = AppConstants.ISO_3166_1_MEXICO
           }
           AppConstants.MALAYSIA -> {
               country = AppConstants.ISO_3166_1_MALAYSIA
           }
           AppConstants.NIGERIA -> {
               country = AppConstants.ISO_3166_1_NIGERIA
           }
           AppConstants.NEW_ZEALAND -> {
               country = AppConstants.ISO_3166_1_NEW_ZEALAND
           }
           AppConstants.PHILIPPINES -> {
               country = AppConstants.ISO_3166_1_PHILIPPINES
           }
       }
    }
    fun getListSourcesNews() = executeUseCase {
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