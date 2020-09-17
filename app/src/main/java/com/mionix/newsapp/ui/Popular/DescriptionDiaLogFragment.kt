package com.mionix.newsapp.ui.Popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.mionix.newsapp.R
import com.mionix.newsapp.viewmodel.ActivityViewModel
import kotlinx.android.synthetic.main.fragment_dialog_description.*
import kotlinx.android.synthetic.main.item_popular_news.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class DescriptionDiaLogFragment:DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //to set The DialogFragment to full screen
        setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_dialog_description,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
    }

    private fun initView() {
        val mArgs = arguments
        tvDescriptionFragmentDialog.text = mArgs?.getString("description")
        tvContentFragmentDialog.text = mArgs?.getString("content")
        ivFragmentDialog
        val url = mArgs?.getString("url")
        if(context!=null){
            Glide.with(context!!)
                .load(url)
                .centerCrop()
                .into(ivFragmentDialog)
        }

        rlFragmentDialog.setOnClickListener {
            this.dismiss()
        }
    }

    private fun initViewModel() {
        activity?.let {
            val sharedViewModel = ViewModelProviders.of(it).get(ActivityViewModel::class.java)
            sharedViewModel.isTouching.observe(viewLifecycleOwner, Observer {
                it?.let {
                    if(!it){
                        this.dismiss()
                    }
                }
            })
        }
    }


}