package com.mionix.newsapp.ui.viewmodel

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mionix.newsapp.model.DataProfile
import com.mionix.newsapp.repo.ProfileRepo
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileRepo: ProfileRepo) : ViewModel() {
    private val _data = MutableLiveData<DataProfile>()
    val data: LiveData<DataProfile> get() = _data
    fun uploadAvatar(uriImageBackground: Uri, contentResolver: ContentResolver) {
        viewModelScope.launch {
            profileRepo.upLoadAvatar(uriImageBackground, contentResolver)
        }
    }
    fun getDataProfile(){
        viewModelScope.launch {
            _data.postValue(profileRepo.getDataProfile())
        }
    }
    fun updateName(name:String){
        profileRepo.updateName(name)
    }
}