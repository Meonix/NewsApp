package com.mionix.newsapp.ui.myaccount

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.mionix.newsapp.R
import com.mionix.newsapp.ui.main.MainActivity
import com.mionix.newsapp.ui.viewmodel.LoginViewModel
import com.mionix.newsapp.ui.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileActivity : AppCompatActivity() {
    private val mProfileViewModel: ProfileViewModel by viewModel()
    private val mLoginViewModel: LoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initView()
        handleOnClick()
        initViewModel()
    }

    private fun initViewModel() {
        mProfileViewModel.getDataProfile()
        mProfileViewModel.data.observe(this@ProfileActivity, Observer {
            initAvatar(null,it.image?.replace("Dot","."))
            etName.setText(it.name)
        })

    }
    private fun initAvatar(uriImageBackground: Uri? , linkImage:String?){
        if(uriImageBackground!=null){
            Glide.with(this@ProfileActivity)
                .load(uriImageBackground)
                .centerCrop()
                .into(ivProfile)
            imgPlaceHolder.visibility = View.GONE
        }
        else if(linkImage != null){
            Glide.with(this@ProfileActivity)
                .load(linkImage)
                .centerCrop()
                .into(ivProfile)
            imgPlaceHolder.visibility = View.GONE
        }
    }
    companion object {
        const val galleryPick = 1
        const val typePickerGallery = "image/*"
    }

    override fun onBackPressed() {
        val mainIntent = Intent(this@ProfileActivity, MainActivity::class.java)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(mainIntent)
        finish()
    }

    private fun handleOnClick() {
        toolbarProfile.ivLeft.setOnClickListener {
            onBackPressed()
        }
        ivProfile.setOnClickListener {
            val galleryIntent = Intent()
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            galleryIntent.type = typePickerGallery
            startActivityForResult(galleryIntent, galleryPick)
        }
        ivEditName.setOnClickListener {
            if(etName.inputType == InputType.TYPE_NULL){
                etName.inputType = InputType.TYPE_CLASS_TEXT
                etName.isFocusableInTouchMode = true

                etName.requestFocus()
                ivEditName.setImageResource(R.drawable.ic_baseline_done_24)
            }else{
                mProfileViewModel.updateName(etName.text.toString())
                etName.inputType = InputType.TYPE_NULL
                ivEditName.setImageResource(R.drawable.ic_baseline_create_24)
            }

        }
        btLogout.setOnClickListener {
            mLoginViewModel.logout()
            onBackPressed()
        }
    }

    private fun initView() {
        etName.inputType = InputType.TYPE_NULL
        initToolBar()
    }

    private fun initToolBar() {
        toolbarProfile.ivRight.visibility = View.GONE
        toolbarProfile.ivLeft.background = ContextCompat.getDrawable(this@ProfileActivity,R.drawable.ic_backpress)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == galleryPick && resultCode == Activity.RESULT_OK && data != null) {
            val uriImageBackground = data.data
            if (uriImageBackground != null) {
                mProfileViewModel.uploadAvatar(uriImageBackground, this.contentResolver)
                initAvatar(uriImageBackground,null)
            } else {
                Toast.makeText(
                    this@ProfileActivity,
                    getString(R.string.upload_file_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


}