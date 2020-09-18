package com.mionix.baseapp.di

import com.mionix.newsapp.ui.viewmodel.ActivityViewModel
import com.mionix.newsapp.ui.viewmodel.PopularNewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PopularNewsViewModel(get()) }
}