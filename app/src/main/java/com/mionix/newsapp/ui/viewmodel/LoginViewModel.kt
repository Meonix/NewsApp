package com.mionix.newsapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mionix.newsapp.model.ListPopularNews
import com.mionix.newsapp.repo.LoginRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepo: LoginRepo): ViewModel() {
    private val _isLogged = MutableLiveData<Boolean>()
    val isLogged: LiveData<Boolean> get() = _isLogged
    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> get() = _isLogin
    fun isLogged(){
        viewModelScope.launch(Dispatchers.IO) {
            _isLogged.postValue(loginRepo.isLogin())
        }
    }
    fun login(email: String, password:String){
        viewModelScope.launch(Dispatchers.IO) {
                loginRepo.login(email,password).addOnCompleteListener {
                    _isLogin.postValue(it.isSuccessful)
                }
        }
    }
    fun createAccount(email:String , password: String){
        loginRepo.createAccountEmail(email,password)
    }
}