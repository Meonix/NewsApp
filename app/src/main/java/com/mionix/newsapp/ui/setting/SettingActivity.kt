package com.mionix.newsapp.ui.setting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.mionix.newsapp.R
import com.mionix.newsapp.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class SettingActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    companion object{
        const val themeKey = "currentTheme"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences(
            "ThemePref",
            Context.MODE_PRIVATE
        )
        setContentView(R.layout.activity_setting)
        initView()
    }
    private fun initToolBar() {
        settingToolbar.ivRight.visibility = View.GONE
        settingToolbar.ivLeft.background = ContextCompat.getDrawable(this@SettingActivity,R.drawable.ic_backpress)
    }
    private fun initView() {
        initSwitch()
        initToolBar()
    }

    private fun initSwitch() {
        when (sharedPreferences.getString(themeKey, null)) {
            "dark" ->  btSwitch.isChecked = true
            "white" ->  btSwitch.isChecked = false
        }
        btSwitch.setOnCheckedChangeListener { compoundButton, b ->
            if(b) {
                sharedPreferences.edit().putString(themeKey, "dark").apply()
            }
            else if(!b) {
                sharedPreferences.edit().putString(themeKey, "white").apply()
            }
            val intent = Intent(this@SettingActivity,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            finish()
            startActivity(intent)
        }
    }
}