package com.mionix.newsapp.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mionix.newsapp.model.Comment
import com.mionix.newsapp.model.CommentSimple
import com.mionix.newsapp.model.DataProfile
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NewsDetailRepo {
    private var mAuth = FirebaseAuth.getInstance()
    private var rootRef = FirebaseDatabase.getInstance().reference
    suspend fun getComment(urlFormatted:String):MutableList<CommentSimple>? = suspendCoroutine { data ->
        val listComment = mutableListOf<CommentSimple>()
        val commentRef = rootRef.child("comment").child(urlFormatted)
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(snapShot in dataSnapshot.children) {
                    val uid = snapShot.child("uid").getValue(String::class.java)
                    val message = snapShot.child("message").getValue(String::class.java)
                    if(message!=null && uid != null){
                        listComment.add(CommentSimple(message,uid))
                    }
                }
                try {
                    data.resume(listComment)

                } catch (e: Exception) {
                    Log.d("DUY", e.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        commentRef.addValueEventListener(eventListener)
    }
    suspend fun getDataProfile(uid:String): DataProfile = suspendCoroutine { data ->
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userRef = dataSnapshot.child("Users")
                val name = userRef.child(uid).child("name").getValue(String::class.java)
                val image = userRef.child(uid).child("avatar").getValue(String::class.java)
                try {
                    data.resume(DataProfile(image, name))
                } catch (e: Exception) {
                    Log.d("DUY", e.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        rootRef.addValueEventListener(eventListener)
    }


    fun uploadComment(message: String, url: String) {
        val commentRef = rootRef.child("comment").push()
        val keyCommentRef = commentRef.key
        mAuth.currentUser?.uid?.let { uid ->
            if (keyCommentRef != null) {
                rootRef.child("comment")
                    .child(url.removeRange(0, 7).replace(".", "Dot").replace("/", "slash"))
                    .child(keyCommentRef).child("message")
                    .setValue(message)
                rootRef.child("comment")
                    .child(url.removeRange(0, 7).replace(".", "Dot").replace("/", "slash"))
                    .child(keyCommentRef).child("uid")
                    .setValue(uid)
            }

        }
    }
}