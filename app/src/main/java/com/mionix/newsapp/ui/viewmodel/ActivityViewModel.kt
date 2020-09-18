package com.mionix.newsapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ActivityViewModel : ViewModel(){
    val isTouching = MutableLiveData(false)
}