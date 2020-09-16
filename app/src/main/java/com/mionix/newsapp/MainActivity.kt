package com.mionix.newsapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import com.mionix.newsapp.adapter.MainHomeViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_dialog_description.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class MainActivity : AppCompatActivity() {
    private var fragmentNames = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initActionOnClick()
    }

    @SuppressLint("RtlHardcoded")
    private fun initActionOnClick() {
        toolbar.ivLeft.setOnClickListener {
            dl.openDrawer(Gravity.LEFT)
        }
    }

    @SuppressLint("RtlHardcoded")
    private fun initView() {
        initDrawer()
        initToolbar()
        initTableLayout()
        initViewPager()
    }


    private fun initViewPager() {
        val mAdapter = MainHomeViewPagerAdapter(fragmentNames,supportFragmentManager)
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
                "Popular"//, "Register Calendar"
            )
        )
    }

    private fun initDrawer() {
        val t = ActionBarDrawerToggle(this@MainActivity,dl, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        dl.addDrawerListener(t)
        t.syncState()
    }
}