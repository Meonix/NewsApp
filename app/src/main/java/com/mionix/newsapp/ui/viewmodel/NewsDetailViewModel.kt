package com.mionix.newsapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mionix.newsapp.model.Comment
import com.mionix.newsapp.model.CommentSimple
import com.mionix.newsapp.model.DataProfile
import com.mionix.newsapp.repo.NewsDetailRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NewsDetailViewModel(private val newsRepo: NewsDetailRepo) : ViewModel() {
    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> get() = _status
    private val _comment = MutableLiveData<MutableList<Comment>>()
    val comment: LiveData<MutableList<Comment>> get() = _comment
    fun uploadComment(message:String,url: String){
        viewModelScope.launch {
                newsRepo.uploadComment(message,url)
                _status.postValue(true)
        }
    }
    fun getComment(urlFormatted:String){
        viewModelScope.launch {
            val listComment = mutableListOf<Comment>()
            val commentSimple = async {
                return@async newsRepo.getComment(urlFormatted)
            }
            var position =0
            while (position< commentSimple.await()?.size ?: 0){
                val message = commentSimple.await()?.get(position)?.message
                val profileInfo = async {
                    return@async commentSimple.await()?.get(position)?.uid?.let {
                        newsRepo.getDataProfile(it)
                    }
                }
                val name = profileInfo.await()?.name
                val avt = profileInfo.await()?.image
                if(message !=null && name != null && avt != null){
                    listComment.add(Comment(message,name,avt))
                }
                position += 1
            }
            _comment.postValue(listComment)
        }
    }
}