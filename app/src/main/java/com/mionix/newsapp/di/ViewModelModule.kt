package com.mionix.baseapp.di

import com.mionix.newsapp.ui.viewmodel.ActivityViewModel
import com.mionix.newsapp.ui.viewmodel.PopularNewsViewModel
import com.mionix.newsapp.ui.viewmodel.SearchNewsViewModel
import com.mionix.newsapp.ui.viewmodel.SourcesNewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PopularNewsViewModel(get()) }
    single { ActivityViewModel() }
    viewModel { SearchNewsViewModel(get()) }
    viewModel { SourcesNewsViewModel(get()) }
}