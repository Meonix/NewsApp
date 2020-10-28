package com.mionix.newsapp.ui.myaccount

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.lifecycle.Observer
import com.mionix.newsapp.R
import com.mionix.newsapp.ui.main.MainActivity
import com.mionix.newsapp.utils.Fun
import com.mionix.newsapp.ui.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.coroutines.resume

class LoginActivity : AppCompatActivity() {
    private val listeners = CopyOnWriteArrayList<MotionLayout.TransitionListener>()
    private val mLoginViewModel: LoginViewModel by viewModel()
    private lateinit var loadingBar : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        initHandleOnClick()
    }

    override fun onBackPressed() {
        val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(mainIntent)
        finish()
    }
    private fun initHandleOnClick() {
        toolbarLogin.ivLeft.setOnClickListener {
            onBackPressed()
        }
        motionLayoutLogin.setOnClickListener {
            when (motionLayoutLogin.currentState) {
                R.id.revealOfLoginButton -> {
                    animationCloseLogin()
                }
                R.id.revealOfRegisterButton -> {
                    animationCloseRegister()
                }
            }
        }
        btLogin.setOnClickListener {
            when (motionLayoutLogin.currentState) {
                R.id.baseState -> {
                    animationOpenLogin()
                }
                R.id.revealOfRegisterButton -> {
                    animationCloseRegister()
                    GlobalScope.launch(Dispatchers.Main) {
                        awaitTransitionComplete(R.id.baseState)
                        animationOpenLogin()
                    }
                }
            }
        }

        btRegister.setOnClickListener {
            when (motionLayoutLogin.currentState) {
                R.id.revealOfLoginButton -> {
                    animationCloseLogin()
                    GlobalScope.launch(Dispatchers.Main) {
                        awaitTransitionComplete(R.id.baseState)
                        animationOpenRegister()
                    }
                }
                R.id.baseState -> {
                    animationOpenRegister()
                }
            }
        }
        btLoginInRevealState.setOnClickListener {
            when (motionLayoutLogin.currentState) {
                R.id.revealOfLoginButton -> {
                    if (!Fun.isEmailValid(etEmail.text.toString()) || etEmail.text.toString()
                            .isEmpty()
                    ) {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.invalid_email),
                            Toast.LENGTH_SHORT
                        ).show()
                        etEmail.requestFocus()
                    } else if (etPass.text.toString().isEmpty()) {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.invalid_password),
                            Toast.LENGTH_SHORT
                        ).show()
                        etPass.requestFocus()
                    } else {
                        login()
                    }
                }
                else -> {
                    if (!Fun.isEmailValid(etEmail.text.toString()) || etEmail.text.toString()
                            .isEmpty()
                    ) {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.invalid_email),
                            Toast.LENGTH_SHORT
                        ).show()
                        etEmail.requestFocus()
                    } else if (etPass.text.toString().isEmpty()) {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.invalid_password),
                            Toast.LENGTH_SHORT
                        ).show()
                        etPass.requestFocus()
                    } else if (etConfirmPass.text.toString().isEmpty()) {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.Invalid_confirm_password),
                            Toast.LENGTH_SHORT
                        ).show()
                        etConfirmPass.requestFocus()
                    } else if (etPass.text.toString() != etConfirmPass.text.toString()) {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.password_and_confirm_password_are_not_the_same),
                            Toast.LENGTH_SHORT
                        ).show()
                        etConfirmPass.requestFocus()
                    } else {
                        createAccount()
                    }
                }
            }
        }
    }

    private fun createAccount() {
        showDialog("Creating account","Please wait, while we are creating new account for you...")
        mLoginViewModel.createAccount(
            etEmail.text.toString().trim(),
            etPass.text.toString().trim()
        )

        mLoginViewModel.isLogin.observe(this@LoginActivity, Observer {
            if (it) {
                loadingBar.dismiss()
                val intent = Intent(this@LoginActivity, ProfileActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    private fun login() {
        showDialog(getString(R.string.title_bar_login),getString(R.string.message_bar_login))
        mLoginViewModel.login(
            etEmail.text.toString().trim(),
            etPass.text.toString().trim()
        )
        mLoginViewModel.isLogin.observe(this@LoginActivity, Observer {
            if (it) {
                loadingBar.dismiss()
                val intent = Intent(this@LoginActivity, ProfileActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                loadingBar.dismiss()
                Toast.makeText(
                    this@LoginActivity,
                    getString(R.string.invalid_account),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun animationCloseLogin() {
        GlobalScope.launch(Dispatchers.Main) {
            motionLayoutLogin.setTransition(R.id.tsCloseRevealLogin)
            motionLayoutLogin.transitionToState(R.id.topStateOfLoginButton)
            awaitTransitionComplete(R.id.topStateOfLoginButton)
            motionLayoutLogin.setTransition(R.id.tsToBotFromLeft)
            motionLayoutLogin.transitionToState(R.id.baseState)
        }
    }

    private fun animationOpenLogin() {
        GlobalScope.launch(Dispatchers.Main) {
            motionLayoutLogin.setTransition(R.id.tsToTopFromLeft)
            motionLayoutLogin.transitionToState(R.id.topStateOfLoginButton)
            awaitTransitionComplete(R.id.topStateOfLoginButton)
            motionLayoutLogin.setTransition(R.id.tsToRevealLogin)
            motionLayoutLogin.transitionToState(R.id.revealOfLoginButton)
        }
    }

    private fun animationOpenRegister() {
        GlobalScope.launch(Dispatchers.Main) {
            motionLayoutLogin.setTransition(R.id.tsToTopFromLeft)
            motionLayoutLogin.transitionToState(R.id.topStateOfRegisterButton)
            awaitTransitionComplete(R.id.topStateOfRegisterButton)
            motionLayoutLogin.setTransition(R.id.tsToRevealRegister)
            motionLayoutLogin.transitionToState(R.id.revealOfRegisterButton)
        }
    }

    private fun animationCloseRegister() {
        GlobalScope.launch(Dispatchers.Main) {
            motionLayoutLogin.setTransition(R.id.tsCloseRevealRegister)
            motionLayoutLogin.transitionToState(R.id.topStateOfRegisterButton)
            awaitTransitionComplete(R.id.topStateOfRegisterButton)
            motionLayoutLogin.setTransition(R.id.tsToBotFromRight)
            motionLayoutLogin.transitionToState(R.id.baseState)
        }
    }
    private fun showDialog(title:String,message:String){
        loadingBar.setTitle(title)
        loadingBar.setMessage(message)
        loadingBar.setCanceledOnTouchOutside(true)
        loadingBar.show()
    }
    private fun initView() {
        initToolBar()
        initMotionLayoutView()
        loadingBar = ProgressDialog(this@LoginActivity)

    }

    private fun initToolBar() {
        toolbarLogin.ivRight.visibility = View.GONE
        toolbarLogin.ivLeft.setImageResource(R.drawable.ic_backpress)
    }

    private fun initMotionLayoutView() {
        motionLayoutLogin.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(
                motionLayout: MotionLayout,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
                listeners.forEach {
                    it.onTransitionTrigger(motionLayout, triggerId, positive, progress)
                }
            }

            override fun onTransitionStarted(motionLayout: MotionLayout, startId: Int, endId: Int) {
                listeners.forEach {
                    it.onTransitionStarted(motionLayout, startId, endId)
                }
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                listeners.forEach {
                    it.onTransitionChange(motionLayout, startId, endId, progress)
                }
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                listeners.forEach {
                    it.onTransitionCompleted(motionLayout, currentId)
                }
            }
        })
    }

    private suspend fun awaitTransitionComplete(transitionId: Int, timeout: Long = 10000L) {
        // If we're already at the specified state, return now
        // Commented because interferes with multi-step animations
//        if (currentState == transitionId) return

        var listener: MotionLayout.TransitionListener? = null

        try {
            withTimeout(timeout) {
                suspendCancellableCoroutine<Unit> { continuation ->
                    val l = object : TransitionAdapter() {
                        override fun onTransitionCompleted(
                            motionLayout: MotionLayout,
                            currentId: Int
                        ) {
                            if (currentId == transitionId) {
                                removeTransitionListener(this)
                                continuation.resume(Unit)
                            }
                        }
                    }
                    // If the coroutine is cancelled, remove the listener
                    continuation.invokeOnCancellation {
                        removeTransitionListener(l)
                    }
                    // And finally add the listener
                    addTransitionListener(l)
                    listener = l
                }
            }
        } catch (tex: TimeoutCancellationException) {
            // Transition didn't happen in time. Remove our listener and throw a cancellation
            // exception to let the coroutine know
            listener?.let(::removeTransitionListener)
            throw CancellationException(
                "Transition to state with id: $transitionId did not" +
                        " complete in timeout.", tex
            )
        }
    }

    private fun addTransitionListener(listener: MotionLayout.TransitionListener) {
        listeners.addIfAbsent(listener)
    }

    fun removeTransitionListener(listener: MotionLayout.TransitionListener) {
        listeners.remove(listener)
    }
}