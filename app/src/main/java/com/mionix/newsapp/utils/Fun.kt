package com.mionix.newsapp.utils

import android.text.TextUtils

object Fun {
    fun isEmailValid(string:String): Boolean {
        return !TextUtils.isEmpty(string) && android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches()
    }
}