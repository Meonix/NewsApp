package com.mionix.newsapp.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.mionix.newsapp.R
import com.mionix.newsapp.ui.main.adapter.MainHomeViewPagerAdapter
import com.mionix.newsapp.ui.search.SearchActivity
import com.mionix.newsapp.ui.viewmodel.ActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private var fragmentNames = mutableListOf<String>()
    private val mActivityViewModel: ActivityViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initActionOnClick()
    }

    companion object {
        const val ALPHA_COLOR = 0.9f
        const val VIEW_GONE = View.GONE
        const val VIEW_VISIBLE = View.VISIBLE
        const val NAME_OF_TAB_POPULAR = "Popular"
        const val NAME_OF_TAB_SOURCES = "Sources News"
    }

    override fun onStart() {
        super.onStart()
        //set Blur
        initBlurForFirstTime()
    }

    private fun initBlurForFirstTime() {
        blurLayout.startBlur()
        blurLayout.alpha =
            ALPHA_COLOR
        blurLayout.visibility =
            VIEW_GONE
    }

    override fun onStop() {
        super.onStop()
        //set Blur
        blurLayout.pauseBlur()
    }

    @SuppressLint("RtlHardcoded")
    private fun initActionOnClick() {
        toolbar.ivLeft.setOnClickListener {
            dl.openDrawer(Gravity.LEFT)
        }
        toolbar.ivRight.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initView() {
        initDrawer()
        initToolbar()
        initTableLayout()
        initViewPager()
        initViewModel()
    }


    private fun initViewModel() {
        mActivityViewModel.isTouching.observe(this@MainActivity, Observer {
            makeBlur(it)
        })
    }

    private fun makeBlur(it: Boolean) {
        when (it) {
            true -> {
                blurLayout.visibility =
                    VIEW_VISIBLE
            }
            false -> {
                blurLayout.visibility =
                    VIEW_GONE
            }
        }
    }


    private fun initViewPager() {
        val mAdapter = MainHomeViewPagerAdapter(fragmentNames, supportFragmentManager)
        mPager.adapter = mAdapter
        // use offscreenPageLimit when want to keep data of each fragment and
        // offscreenPageLimit default = 1

        //mPager.offscreenPageLimit = 5
        tabLayout.setupWithViewPager(mPager)
    }

    @SuppressLint("RestrictedApi")
    private fun initToolbar() {
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.setHomeButtonEnabled(false)
    }

    private fun initTableLayout() {
        fragmentNames.addAll(
            mutableListOf(
                NAME_OF_TAB_POPULAR,NAME_OF_TAB_SOURCES
            )
        )
    }

    private fun initDrawer() {
        val t = ActionBarDrawerToggle(
            this@MainActivity, dl,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        dl.addDrawerListener(t)
        t.syncState()
    }
}