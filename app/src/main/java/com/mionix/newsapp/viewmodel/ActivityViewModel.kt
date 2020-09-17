package com.mionix.newsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ActivityViewModel : ViewModel(){
    val isTouching = MutableLiveData<Boolean>()
}