package com.mionix.newsapp.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.mionix.newsapp.R
import com.mionix.newsapp.ui.viewmodel.ActivityViewModel
import kotlinx.android.synthetic.main.fragment_dialog_description.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class DescriptionDiaLogFragment : DialogFragment() {
    private val sharedViewModel: ActivityViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //to set The DialogFragment to full screen
        setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen)
    }

    companion object {
        const val KEY_DESCRIPTION = "description"
        const val KEY_CONTENT = "content"
        const val KEY_URL = "url"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_dialog_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
    }

    private fun initView() {
        val mArgs = arguments
        tvDescriptionFragmentDialog.text = mArgs?.getString(KEY_DESCRIPTION)
        tvContentFragmentDialog.text = mArgs?.getString(KEY_CONTENT)
        ivFragmentDialog
        val url = mArgs?.getString(KEY_URL)
        Glide.with(this@DescriptionDiaLogFragment)
            .load(url)
            .centerCrop()
            .into(ivFragmentDialog)

        rlFragmentDialog.setOnClickListener {
            this.dismiss()
        }
    }

    private fun initViewModel() {
        sharedViewModel.isTouching.observe(viewLifecycleOwner, Observer {
            if (!it) {
                this.dismiss()
            }
        })
    }


}