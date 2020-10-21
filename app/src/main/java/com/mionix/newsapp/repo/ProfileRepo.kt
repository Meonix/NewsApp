package com.mionix.newsapp.repo

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.mionix.newsapp.model.DataProfile
import java.io.ByteArrayOutputStream
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ProfileRepo {
    private var mAuth = FirebaseAuth.getInstance()
    private var rootRef = FirebaseDatabase.getInstance().reference
    suspend fun getDataProfile(): DataProfile = suspendCoroutine { data ->
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val name = mAuth.currentUser?.uid?.let {
                    dataSnapshot.child("Users").child(it).child("name").getValue(String::class.java)
                }
                val image = mAuth.currentUser?.uid?.let {
                    dataSnapshot.child("Users").child(it).child("avatar")
                        .getValue(String::class.java)
                }
                try {
                    data.resume(DataProfile(image, name))

                } catch (e:Exception) {
                    Log.d("DUY", e.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        rootRef.addValueEventListener(eventListener)
    }

    fun upLoadAvatar(uriImageBackground: Uri, contentResolver: ContentResolver) {
        val userAvatarStoreRef = FirebaseStorage.getInstance().reference.child("Avatar")
        val filePath = userAvatarStoreRef.child(mAuth.currentUser?.uid + ".jpg")
        compressImage(uriImageBackground, contentResolver)?.toByteArray()?.let {
            filePath.putBytes(it).continueWithTask { continueTask ->
                if (!continueTask.isSuccessful) {
                    continueTask.exception?.let { ex ->
                        throw ex
                    }
                }
                filePath.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    Log.d("DUY", downloadUri.toString())
                    mAuth.currentUser?.uid?.let { uid ->
                        rootRef.child("Users").child(uid).child("avatar")
                            .setValue(downloadUri.toString().replace(".", "Dot"))
                    }
                } else {
                }
            }
        }
    }
    fun updateName(name:String){
        mAuth.currentUser?.uid?.let { uid ->
            rootRef.child("Users").child(uid).child("name")
                .setValue(name)
        }
    }

    private fun compressImage(
        image: Uri,
        contentResolver: ContentResolver
    ): ByteArrayOutputStream? {
        var original: Bitmap?
        try {
            original = MediaStore.Images.Media.getBitmap(contentResolver, image)
        } catch (e: IOException) {
            return null
        }

        val height = original!!.height
        var width = original.width
        if (height > 2048) {
            width = (width * (2048.0f / height)).toInt()
            original = getResizedBitmap(original, width, 2048)
        }

        val out = ByteArrayOutputStream()
        original.compress(Bitmap.CompressFormat.JPEG, 47, out)
        return out
    }

    private fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        val width = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // CREATE A MATRIX FOR THE MANIPULATION
        val matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight)

        // "RECREATE" THE NEW BITMAP
        return Bitmap.createBitmap(
            bm, 0, 0, width, height, matrix, false
        )
    }
}