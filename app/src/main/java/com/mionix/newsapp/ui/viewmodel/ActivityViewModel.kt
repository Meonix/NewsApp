package com.mionix.newsapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.mionix.newsapp.model.ListPopularNews
import com.mionix.newsapp.repo.LoginRepo


class ActivityViewModel() : ViewModel(){
    val isTouching = MutableLiveData(false)
}