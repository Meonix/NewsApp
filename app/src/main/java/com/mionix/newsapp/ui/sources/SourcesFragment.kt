package com.mionix.newsapp.ui.sources

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mionix.newsapp.R
import com.mionix.newsapp.model.AllSource
import com.mionix.newsapp.ui.popular.adapter.SpinnerAdapter
import com.mionix.newsapp.ui.sources.adapter.SourcesListAdapter
import com.mionix.newsapp.ui.viewmodel.PopularNewsViewModel
import com.mionix.newsapp.ui.viewmodel.SourcesNewsViewModel
import com.mionix.newsapp.ui.viewmodel.SpinnerViewModel
import com.mionix.newsapp.utils.AppConstants
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.android.synthetic.main.fragment_sources.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SourcesFragment : Fragment() {
    private val mSourcesNewsViewModel: SourcesNewsViewModel by viewModel()
    private var sourceListAdapter = SourcesListAdapter(mutableListOf())
    private val spinnerViewModel : SpinnerViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sources, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        iniListSourcesNews()
        initView()
    }

    private fun initView() {
        initSpinner()
    }

    private fun iniListSourcesNews() {
        val linearLayoutManager = LinearLayoutManager(context)
        rvSources.adapter = sourceListAdapter
        rvSources.layoutManager = linearLayoutManager
        sourceListAdapter.notifyDataSetChanged()
        sourceListAdapter.onItemClick = {
            val intent = Intent(context,SourceDetail::class.java)
            intent.putExtra("urlSource",it.url)
            startActivity(intent)
        }
    }

    private fun initViewModel() {
        getSourceListOnCountry()
        mSourcesNewsViewModel.getListSourcesNews.observe(viewLifecycleOwner, Observer {
            sourceListAdapter.updateData(it.sources)
        })
    }
    private fun getSourceListOnCountry() {
        mSourcesNewsViewModel.getListSourcesNews()
    }
    private fun initSpinner() {
        val arrayAdapterLeft = context?.let {
            SpinnerAdapter(
                it,
                spinnerViewModel.spinnerItem
            )
        }
        spSource.adapter = arrayAdapterLeft
        spSource.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                sourceListAdapter.clearData()
                mSourcesNewsViewModel.getDataByCountryOfItemSpinner(spSource.selectedItem.toString())
                getSourceListOnCountry()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }
        }
    }
    companion object {
        fun newInstance(): SourcesFragment {
            val args = Bundle()
            val fragment = SourcesFragment()
            fragment.arguments = args
            return fragment
        }
    }
}