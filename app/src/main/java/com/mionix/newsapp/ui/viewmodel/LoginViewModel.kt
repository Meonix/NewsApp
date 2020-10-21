package com.mionix.newsapp.ui.viewmodel

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mionix.newsapp.repo.LoginRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepo: LoginRepo) : ViewModel() {
    private val _checkLogged = MutableLiveData<Boolean>()
    val checkLogged: LiveData<Boolean> get() = _checkLogged
    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> get() = _isLogin
    fun checkLogged() {
        viewModelScope.launch(Dispatchers.IO) {
            _checkLogged.postValue(loginRepo.isLogin())
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepo.login(email, password).addOnCompleteListener {
                _isLogin.postValue(it.isSuccessful)
            }
        }
    }

    fun createAccount(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepo.createAccountEmail(email, password).addOnCompleteListener {
                _isLogin.postValue(it.isSuccessful)
            }
        }
    }
    fun logout(){
        loginRepo.logout()
    }
}