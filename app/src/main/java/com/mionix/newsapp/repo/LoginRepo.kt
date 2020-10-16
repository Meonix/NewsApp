package com.mionix.newsapp.repo

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class LoginRepo {
    var mAuth = FirebaseAuth.getInstance()

    suspend fun isLogin(): Boolean = coroutineScope {
        if (FirebaseAuth.getInstance().currentUser != null) {
            return@coroutineScope true
        }
        return@coroutineScope false
    }

    fun createAccountEmail(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
    }

    fun login(email: String, password: String): Task<AuthResult> {
        return mAuth.signInWithEmailAndPassword(email, password)
    }

    fun logout() {
        mAuth.signOut()
    }
}