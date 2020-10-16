package com.mionix.baseapp.di

import com.mionix.newsapp.ui.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PopularNewsViewModel(get()) }
    single { ActivityViewModel() }
    viewModel { SearchNewsViewModel(get()) }
    viewModel { SourcesNewsViewModel(get()) }
    viewModel { SpinnerViewModel() }
    viewModel { LoginViewModel(get()) }
}