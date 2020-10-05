package com.mionix.newsapp.ui.sources

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
import com.mionix.newsapp.utils.AppConstants
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.android.synthetic.main.fragment_sources.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SourcesFragment : Fragment() {
    private val mSourcesNews: SourcesNewsViewModel by viewModel()
    private var sourceListAdapter = SourcesListAdapter(mutableListOf())
    private var country = ""
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
    }

    private fun initViewModel() {
        getSourceListOnCountry()
        mSourcesNews.getListSourcesNews.observe(viewLifecycleOwner, Observer {
            sourceListAdapter.updateData(it.sources)
        })
    }
    private fun getSourceListOnCountry() {
        mSourcesNews.getListSourcesNews(country)
    }
    private fun initSpinner() {
        val spinnerLeft = listOf(
            AppConstants.ARGENTINA,
            AppConstants.UNITED_ARAB_EMIRATES,
            AppConstants.AUSTRIA,
            AppConstants.AUSTRALIA,
            AppConstants.BELGIUM,
            AppConstants.BULGARIA,
            AppConstants.BRAZIL,
            AppConstants.CANADA,
            AppConstants.SWITZERLAND,
            AppConstants.CHINA,
            AppConstants.COLUMBIA,
            AppConstants.CUBA,
            AppConstants.CZECHIA,
            AppConstants.GERMANY,
            AppConstants.EGYPT,
            AppConstants.FRANCE,
            AppConstants.UK_OF_GB_AND_NI,
            AppConstants.GREECE,
            AppConstants.HONG_KONG,
            AppConstants.HUNGARY,
            AppConstants.INDONESIA,
            AppConstants.IRELAND,
            AppConstants.ISRAEL,
            AppConstants.INDIA,
            AppConstants.ITALY,
            AppConstants.JAPAN,
            AppConstants.KOREA,
            AppConstants.LITHUANIA,
            AppConstants.LATVIA,
            AppConstants.MOROCCO,
            AppConstants.MEXICO,
            AppConstants.MALAYSIA,
            AppConstants.NIGERIA,
            AppConstants.NEW_ZEALAND,
            AppConstants.PHILIPPINES
        )
        val arrayAdapterLeft = context?.let {
            SpinnerAdapter(
                it,
                spinnerLeft
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
                getDataByCountryOfItemSpinner()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }
        }
    }
    private fun getDataByCountryISO_3166_1(countryISO_3166_1: String) {
        sourceListAdapter.clearData()
        country = countryISO_3166_1
        getSourceListOnCountry()
    }

    private fun getDataByCountryOfItemSpinner() {
        when (spSource.selectedItem.toString()) {
            AppConstants.ARGENTINA -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_ARGENTINA)
            }
            AppConstants.UNITED_ARAB_EMIRATES -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_UNITED_ARAB_EMIRATES)
            }
            AppConstants.AUSTRIA -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_AUSTRIA)
            }
            AppConstants.AUSTRALIA -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_AUSTRALIA)
            }
            AppConstants.BELGIUM -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_BELGIUM)
            }
            AppConstants.BULGARIA -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_BULGARIA)
            }
            AppConstants.BRAZIL -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_BRAZIL)
            }
            AppConstants.CANADA -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_CANADA)
            }
            AppConstants.SWITZERLAND -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_SWITZERLAND)
            }
            AppConstants.CHINA -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_CHINA)
            }
            AppConstants.COLUMBIA -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_COLUMBIA)
            }
            AppConstants.CUBA -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_CUBA)
            }
            AppConstants.CZECHIA -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_CZECHIA)
            }
            AppConstants.GERMANY -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_GERMANY)
            }
            AppConstants.EGYPT -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_EGYPT)
            }
            AppConstants.FRANCE -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_FRANCE)
            }
            AppConstants.UK_OF_GB_AND_NI -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_UK_OF_GB_AND_NI)
            }
            AppConstants.GREECE -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_GREECE)
            }
            AppConstants.HONG_KONG -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_HONG_KONG)
            }
            AppConstants.HUNGARY -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_HUNGARY)
            }
            AppConstants.INDONESIA -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_INDONESIA)
            }
            AppConstants.IRELAND -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_IRELAND)
            }
            AppConstants.ISRAEL -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_ISRAEL)
            }
            AppConstants.INDIA -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_INDIA)
            }
            AppConstants.ITALY -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_ITALY)
            }
            AppConstants.JAPAN -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_JAPAN)
            }
            AppConstants.KOREA -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_KOREA)
            }
            AppConstants.LITHUANIA -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_LITHUANIA)
            }
            AppConstants.LATVIA -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_LATVIA)
            }
            AppConstants.MOROCCO -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_MOROCCO)
            }
            AppConstants.MEXICO -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_MEXICO)
            }
            AppConstants.MALAYSIA -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_MALAYSIA)
            }
            AppConstants.NIGERIA -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_NIGERIA)
            }
            AppConstants.NEW_ZEALAND -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_NEW_ZEALAND)
            }
            AppConstants.PHILIPPINES -> {
                getDataByCountryISO_3166_1(AppConstants.ISO_3166_1_PHILIPPINES)
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