package com.mionix.newsapp.ui.popular

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mionix.newsapp.R
import com.mionix.newsapp.utils.AppConstants.ARGENTINA
import com.mionix.newsapp.utils.AppConstants.AUSTRALIA
import com.mionix.newsapp.utils.AppConstants.AUSTRIA
import com.mionix.newsapp.utils.AppConstants.BELGIUM
import com.mionix.newsapp.utils.AppConstants.BRAZIL
import com.mionix.newsapp.utils.AppConstants.BULGARIA
import com.mionix.newsapp.utils.AppConstants.CANADA
import com.mionix.newsapp.utils.AppConstants.CHINA
import com.mionix.newsapp.utils.AppConstants.COLUMBIA
import com.mionix.newsapp.utils.AppConstants.CUBA
import com.mionix.newsapp.utils.AppConstants.CZECHIA
import com.mionix.newsapp.utils.AppConstants.EGYPT
import com.mionix.newsapp.utils.AppConstants.FRANCE
import com.mionix.newsapp.utils.AppConstants.GERMANY
import com.mionix.newsapp.utils.AppConstants.GREECE
import com.mionix.newsapp.utils.AppConstants.HONG_KONG
import com.mionix.newsapp.utils.AppConstants.HUNGARY
import com.mionix.newsapp.utils.AppConstants.INDIA
import com.mionix.newsapp.utils.AppConstants.INDONESIA
import com.mionix.newsapp.utils.AppConstants.IRELAND
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_ARGENTINA
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_AUSTRALIA
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_AUSTRIA
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_BELGIUM
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_BRAZIL
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_BULGARIA
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_CANADA
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_CHINA
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_COLUMBIA
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_CUBA
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_CZECHIA
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_EGYPT
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_FRANCE
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_GERMANY
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_GREECE
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_HONG_KONG
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_HUNGARY
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_INDIA
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_INDONESIA
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_IRELAND
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_ISRAEL
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_ITALY
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_JAPAN
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_KOREA
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_LATVIA
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_LITHUANIA
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_MALAYSIA
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_MEXICO
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_MOROCCO
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_NEW_ZEALAND
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_NIGERIA
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_PHILIPPINES
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_SWITZERLAND
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_UK_OF_GB_AND_NI
import com.mionix.newsapp.utils.AppConstants.ISO_3166_1_UNITED_ARAB_EMIRATES
import com.mionix.newsapp.utils.AppConstants.ISRAEL
import com.mionix.newsapp.utils.AppConstants.ITALY
import com.mionix.newsapp.utils.AppConstants.JAPAN
import com.mionix.newsapp.utils.AppConstants.KOREA
import com.mionix.newsapp.utils.AppConstants.LATVIA
import com.mionix.newsapp.utils.AppConstants.LITHUANIA
import com.mionix.newsapp.utils.AppConstants.MALAYSIA
import com.mionix.newsapp.utils.AppConstants.MEXICO
import com.mionix.newsapp.utils.AppConstants.MOROCCO
import com.mionix.newsapp.utils.AppConstants.NEW_ZEALAND
import com.mionix.newsapp.utils.AppConstants.NIGERIA
import com.mionix.newsapp.utils.AppConstants.PHILIPPINES
import com.mionix.newsapp.utils.AppConstants.SWITZERLAND
import com.mionix.newsapp.utils.AppConstants.TIME_DELAY
import com.mionix.newsapp.utils.AppConstants.UK_OF_GB_AND_NI
import com.mionix.newsapp.utils.AppConstants.UNITED_ARAB_EMIRATES
import com.mionix.newsapp.ui.popular.adapter.PopularNewsListAdapter
import com.mionix.newsapp.ui.popular.adapter.SpinnerAdapter
import com.mionix.newsapp.ui.viewmodel.ActivityViewModel
import com.mionix.newsapp.ui.viewmodel.PopularNewsViewModel

import kotlinx.android.synthetic.main.fragment_popular.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class PopularFragment : Fragment() {
    private val mPopularNews: PopularNewsViewModel by viewModel()
    private var mPopularNewsListAdapter = PopularNewsListAdapter(mutableListOf())
    private val mActivityViewModel: ActivityViewModel by viewModel()
    private lateinit var popularNewsLayoutManager: LinearLayoutManager

    private var page = 1
    private var country = ""

    companion object {
        fun newInstance(): PopularFragment {
            val args = Bundle()
            val fragment = PopularFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initViewModel()
        initSpinner()
        initRecycleView()
        initSwipeRefresh()
    }

    private fun initSwipeRefresh() {
        swipeRefreshPopular.setOnRefreshListener {
            getDataByCountryOfItemSpinner()
        }
    }

    private fun getDataByCountryOfItemSpinner() {
        when (spMain.selectedItem.toString()) {
            ARGENTINA -> {
                getDataByCountryISO_3166_1(ISO_3166_1_ARGENTINA)
            }
            UNITED_ARAB_EMIRATES -> {
                getDataByCountryISO_3166_1(ISO_3166_1_UNITED_ARAB_EMIRATES)
            }
            AUSTRIA -> {
                getDataByCountryISO_3166_1(ISO_3166_1_AUSTRIA)
            }
            AUSTRALIA -> {
                getDataByCountryISO_3166_1(ISO_3166_1_AUSTRALIA)
            }
            BELGIUM -> {
                getDataByCountryISO_3166_1(ISO_3166_1_BELGIUM)
            }
            BULGARIA -> {
                getDataByCountryISO_3166_1(ISO_3166_1_BULGARIA)
            }
            BRAZIL -> {
                getDataByCountryISO_3166_1(ISO_3166_1_BRAZIL)
            }
            CANADA -> {
                getDataByCountryISO_3166_1(ISO_3166_1_CANADA)
            }
            SWITZERLAND -> {
                getDataByCountryISO_3166_1(ISO_3166_1_SWITZERLAND)
            }
            CHINA -> {
                getDataByCountryISO_3166_1(ISO_3166_1_CHINA)
            }
            COLUMBIA -> {
                getDataByCountryISO_3166_1(ISO_3166_1_COLUMBIA)
            }
            CUBA -> {
                getDataByCountryISO_3166_1(ISO_3166_1_CUBA)
            }
            CZECHIA -> {
                getDataByCountryISO_3166_1(ISO_3166_1_CZECHIA)
            }
            GERMANY -> {
                getDataByCountryISO_3166_1(ISO_3166_1_GERMANY)
            }
            EGYPT -> {
                getDataByCountryISO_3166_1(ISO_3166_1_EGYPT)
            }
            FRANCE -> {
                getDataByCountryISO_3166_1(ISO_3166_1_FRANCE)
            }
            UK_OF_GB_AND_NI -> {
                getDataByCountryISO_3166_1(ISO_3166_1_UK_OF_GB_AND_NI)
            }
            GREECE -> {
                getDataByCountryISO_3166_1(ISO_3166_1_GREECE)
            }
            HONG_KONG -> {
                getDataByCountryISO_3166_1(ISO_3166_1_HONG_KONG)
            }
            HUNGARY -> {
                getDataByCountryISO_3166_1(ISO_3166_1_HUNGARY)
            }
            INDONESIA -> {
                getDataByCountryISO_3166_1(ISO_3166_1_INDONESIA)
            }
            IRELAND -> {
                getDataByCountryISO_3166_1(ISO_3166_1_IRELAND)
            }
            ISRAEL -> {
                getDataByCountryISO_3166_1(ISO_3166_1_ISRAEL)
            }
            INDIA -> {
                getDataByCountryISO_3166_1(ISO_3166_1_INDIA)
            }
            ITALY -> {
                getDataByCountryISO_3166_1(ISO_3166_1_ITALY)
            }
            JAPAN -> {
                getDataByCountryISO_3166_1(ISO_3166_1_JAPAN)
            }
            KOREA -> {
                getDataByCountryISO_3166_1(ISO_3166_1_KOREA)
            }
            LITHUANIA -> {
                getDataByCountryISO_3166_1(ISO_3166_1_LITHUANIA)
            }
            LATVIA -> {
                getDataByCountryISO_3166_1(ISO_3166_1_LATVIA)
            }
            MOROCCO -> {
                getDataByCountryISO_3166_1(ISO_3166_1_MOROCCO)
            }
            MEXICO -> {
                getDataByCountryISO_3166_1(ISO_3166_1_MEXICO)
            }
            MALAYSIA -> {
                getDataByCountryISO_3166_1(ISO_3166_1_MALAYSIA)
            }
            NIGERIA -> {
                getDataByCountryISO_3166_1(ISO_3166_1_NIGERIA)
            }
            NEW_ZEALAND -> {
                getDataByCountryISO_3166_1(ISO_3166_1_NEW_ZEALAND)
            }
            PHILIPPINES -> {
                getDataByCountryISO_3166_1(ISO_3166_1_PHILIPPINES)
            }
        }
    }

    private fun initViewModel() {
        getNewsListOnCountry()
        mPopularNews.getListPopularNews.observe(viewLifecycleOwner, Observer {
            mPopularNewsListAdapter.updateData(it.articles)
            swipeRefreshPopular.isRefreshing = false
        })
    }


    private fun initSpinner() {
        val spinnerLeft = listOf(
            ARGENTINA, UNITED_ARAB_EMIRATES, AUSTRIA, AUSTRALIA, BELGIUM, BULGARIA,
            BRAZIL, CANADA, SWITZERLAND, CHINA, COLUMBIA, CUBA, CZECHIA, GERMANY,
            EGYPT, FRANCE, UK_OF_GB_AND_NI, GREECE, HONG_KONG, HUNGARY, INDONESIA,
            IRELAND, ISRAEL, INDIA, ITALY, JAPAN, KOREA, LITHUANIA, LATVIA, MOROCCO,
            MEXICO, MALAYSIA, NIGERIA, NEW_ZEALAND, PHILIPPINES
        )
        val arrayAdapterLeft = context?.let {
            SpinnerAdapter(
                it,
                spinnerLeft
            )
        }
        spMain.adapter = arrayAdapterLeft
        spMain.onItemSelectedListener = object :
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
        mPopularNewsListAdapter.clearData()
        page = 1
        country = countryISO_3166_1
        getNewsListOnCountry()
    }

    private fun getNewsListOnCountry() {
        mPopularNews.getListPopularNews(page, country)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initRecycleView() {
        rvPopular.adapter = mPopularNewsListAdapter
        popularNewsLayoutManager = LinearLayoutManager(context)
        rvPopular.layoutManager = popularNewsLayoutManager
        // Way 1 is Using CoordinatorLayout AppBarLayout and CollapsingToolbarLayout
        rvPopular.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    //When scroll Blur view will gone
                    mActivityViewModel.isTouching.postValue(false)
                    //load more
                    val visibleItemCount = popularNewsLayoutManager.childCount
                    val pastVisibleItem =
                        popularNewsLayoutManager.findFirstCompletelyVisibleItemPosition()
                    val total = mPopularNewsListAdapter.itemCount
                    if (mPopularNews.isLoading) {
                        if ((visibleItemCount + pastVisibleItem) >= total) {
                            page += 1

                            getMorePopularNews()
                            mPopularNews.isLoading = false
                        }
                    }
                }
            }
        })
        handleOnClickItemView()
    }


    private fun getMorePopularNews() {
        popularProgressBar.visibility = View.VISIBLE
        Handler().postDelayed({
            getNewsListOnCountry()
            popularProgressBar.visibility = View.GONE
            mPopularNews.isLoading = true
        }, TIME_DELAY)
    }

    private fun handleOnClickItemView() {
        mPopularNewsListAdapter.onItemLongClick = { description, content, url ->
            showFragmentDialog(description, content, url)
        }
        mPopularNewsListAdapter.onItemTouchClick = { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    mActivityViewModel.isTouching.postValue(false)
                }
            }
        }
        mPopularNewsListAdapter.onItemClick = {
            val intent = Intent(context,NewsDetail::class.java)
            intent.putExtra("urlWebView",it)
            startActivity(intent)
        }
    }

    private fun showFragmentDialog(description: String, content: String, url: String) {
        val dialog = DescriptionDiaLogFragment()
        mActivityViewModel.isTouching.postValue(true)
        val args = Bundle()
        args.putString(DescriptionDiaLogFragment.KEY_DESCRIPTION, description)
        args.putString(DescriptionDiaLogFragment.KEY_CONTENT, content)
        args.putString(DescriptionDiaLogFragment.KEY_URL, url)
        dialog.arguments = args
        dialog.show(parentFragmentManager.beginTransaction(), "Fragment Dialog")
    }


}
