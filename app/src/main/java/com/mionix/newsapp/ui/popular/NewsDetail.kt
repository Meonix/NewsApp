package com.mionix.newsapp.ui.popular

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mionix.newsapp.R
import com.mionix.newsapp.model.Comment
import com.mionix.newsapp.ui.myaccount.LoginActivity
import com.mionix.newsapp.ui.myaccount.ProfileActivity
import com.mionix.newsapp.ui.popular.adapter.CommentAdapter
import com.mionix.newsapp.ui.popular.adapter.PopularNewsListAdapter
import com.mionix.newsapp.ui.viewmodel.LoginViewModel
import com.mionix.newsapp.ui.viewmodel.NewsDetailViewModel
import com.mionix.newsapp.ui.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_news_detail.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.coroutines.resume

class NewsDetail : AppCompatActivity() {
    private val mNewsDetailViewModel : NewsDetailViewModel by viewModel()
    private val mProfileViewModel : ProfileViewModel by viewModel()

    private val mLoginViewModel: LoginViewModel by viewModel()
    private val listeners = CopyOnWriteArrayList<MotionLayout.TransitionListener>()
    private var url = ""
    private lateinit var newsDetailLayoutManager: LinearLayoutManager
    private var mCommentAdapter = CommentAdapter(mutableListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        initView()
        initActionClick()
        initViewModel()
    }

    private fun initViewModel() {
        mNewsDetailViewModel.getComment(url.removeRange(0, 7).replace(".", "Dot").replace("/", "slash"))
        mNewsDetailViewModel.comment.observe(this@NewsDetail, Observer {
            mCommentAdapter.updateData(it)
        })
    }

    private fun initView() {
        initCommentSection()
        initToolBar()
        initWebView()
        initMotionLayoutView()
        initRecycleView()
    }

    private fun initCommentSection() {
        mLoginViewModel.checkLogged()
        mLoginViewModel.checkLogged.observe(this@NewsDetail, Observer { islogged ->
                if (!islogged){
                    etComment.hint = "You have to login to send comment"
                    btComment.visibility = View.GONE
                    etComment.inputType = InputType.TYPE_NULL
                }
        })
    }

    private fun initRecycleView() {
        rvDetail.adapter = mCommentAdapter
        newsDetailLayoutManager = LinearLayoutManager(this@NewsDetail)
        rvDetail.layoutManager = newsDetailLayoutManager
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        intent.getStringExtra("urlWebView")?.let { url =it }
        Log.d("DUY", url)
        webViewDetail.settings.javaScriptEnabled = true

        webViewDetail.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let {
                    view?.loadUrl(it)
                }

                return true
            }
        }
        webViewDetail.loadUrl(url)
    }

    private fun initToolBar() {
        detailToolbar.ivRight.visibility = View.GONE
        detailToolbar.ivLeft.background = ContextCompat.getDrawable(this@NewsDetail,R.drawable.ic_backpress)
    }
    @SuppressLint("ClickableViewAccessibility")
    private fun initActionClick() {
        detailToolbar.ivLeft.setOnClickListener {
            onBackPressed()
        }
        cvDetail.setOnClickListener {
            if(motionLayoutDetail.currentState == R.id.hide){
                animationOpenComment()
            }
            else if(motionLayoutDetail.currentState == R.id.show){
                animationCloseComment()
            }
        }

        btComment.setOnClickListener {
            mNewsDetailViewModel.uploadComment(etComment.text.toString(),url)
            mProfileViewModel.getDataProfile()
            mProfileViewModel.data.observe(this,{
                if(it.name!=null && it.image != null && etComment.text.toString() != ""){
                    mCommentAdapter.insertData(Comment(etComment.text.toString(),it.name,it.image))
                    etComment.setText("")
                    rvDetail.smoothScrollToPosition(mCommentAdapter.itemCount -1)
                }
            })
        }
    }

    private fun animationOpenComment() {
        tvComment.visibility = View.GONE
        GlobalScope.launch(Dispatchers.Main) {
            motionLayoutDetail.setTransition(R.id.toShow)
            motionLayoutDetail.transitionToState(R.id.show)
        }

    }
    private fun animationCloseComment() {
        GlobalScope.launch(Dispatchers.Main) {
            motionLayoutDetail.setTransition(R.id.toHide)
            motionLayoutDetail.transitionToState(R.id.hide)
            awaitTransitionComplete(R.id.hide)
            tvComment.visibility = View.VISIBLE
        }

    }
    private fun initMotionLayoutView() {
        motionLayoutDetail.setTransitionListener(object : MotionLayout.TransitionListener {
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