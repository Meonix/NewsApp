package com.mionix.newsapp.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mionix.newsapp.R
import com.mionix.newsapp.utils.AppConstants
import com.mionix.newsapp.ui.main.MainActivity
import com.mionix.newsapp.ui.popular.DescriptionDiaLogFragment
import com.mionix.newsapp.ui.popular.NewsDetail
import com.mionix.newsapp.ui.popular.adapter.PopularNewsListAdapter
import com.mionix.newsapp.ui.popular.adapter.SpinnerAdapter
import com.mionix.newsapp.ui.viewmodel.ActivityViewModel
import com.mionix.newsapp.ui.viewmodel.SearchNewsViewModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {
    private val mSearchNews: SearchNewsViewModel by viewModel()
    private var mSearchNewsListAdapter = PopularNewsListAdapter(mutableListOf())
    private lateinit var searchNewsLayoutManager: LinearLayoutManager
    private var page = 1
    private var language = ""
    private val mActivityViewModel: ActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
        initViewModel()
    }

    override fun onStart() {
        super.onStart()
        //set Blur
        initBlurForFirstTime()
    }

    private fun initBlurForFirstTime() {
        blurSearchLayout.startBlur()
        blurSearchLayout.alpha = MainActivity.ALPHA_COLOR
        blurSearchLayout.visibility = MainActivity.VIEW_GONE
    }

    override fun onStop() {
        super.onStop()
        //set Blur
        blurSearchLayout.pauseBlur()
    }

    private fun initView() {
        initSpinner()
        initToolBar()
        initActionClick()
        initRecycleView()
        initEditTextSearch()
        initSwipeRefresh()
    }

    private fun initSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            getDataByLanguageOfItemSpinner()
        }
    }

    private fun initEditTextSearch() {
        searchToolbar.etToolbar.addTextChangedListener(object : TextWatcher {
            private var searchFor = ""
            override fun afterTextChanged(p0: Editable?) {
                val searchText = p0.toString().trim()
                if (searchText == searchFor)
                    return
                searchFor = searchText
                GlobalScope.launch {
                    delay(1500)  //debounce timeOut
                    if (searchText != searchFor)
                        return@launch
                    withContext(Dispatchers.Main) {
                        mSearchNewsListAdapter.clearData()
                    }
                    Log.d("DUY", p0.toString())
                    getNewsListOnLanguage(p0.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }
        )
    }

    private fun initViewModel() {
        mSearchNews.getListSearchNews.observe(this@SearchActivity, Observer {
            mSearchNewsListAdapter.updateData(it.articles)
            swipeRefresh.isRefreshing = false
        })
    }

    private fun getNewsListOnLanguage(keyWord: String) {
        mSearchNews.getListSearchNews(keyWord, page, language)
    }

    private fun initRecycleView() {
        rvSearch.adapter = mSearchNewsListAdapter
        searchNewsLayoutManager = LinearLayoutManager(this@SearchActivity)
        rvSearch.layoutManager = searchNewsLayoutManager
        // Way 1 is Using CoordinatorLayout AppBarLayout and CollapsingToolbarLayout
        rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    //When scroll Blur view will gone
                    //mActivityViewModel.isTouching.postValue(false)
                    //load more
                    val visibleItemCount = searchNewsLayoutManager.childCount
                    val pastVisibleItem =
                        searchNewsLayoutManager.findFirstCompletelyVisibleItemPosition()
                    val total = mSearchNewsListAdapter.itemCount
                    if (mSearchNews.isLoading) {
                        if ((visibleItemCount + pastVisibleItem) >= total) {
                            page += 1

                            getMoreSearchNews()
                            mSearchNews.isLoading = false
                        }
                    }
                }
            }
        })
        handleOnClickItemView()
    }

    private fun handleOnClickItemView() {
        mSearchNewsListAdapter.onItemLongClick = { description, content, url ->
            showFragmentDialog(description, content, url)
        }
        mSearchNewsListAdapter.onItemTouchClick = { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    blurSearchLayout.visibility = View.GONE
                    mActivityViewModel.isTouching.postValue(false)
                }
            }
        }
        mSearchNewsListAdapter.onItemClick = {
            val intent = Intent(this@SearchActivity, NewsDetail::class.java)
            intent.putExtra("urlWebView",it)
            startActivity(intent)
        }
    }

    companion object {

    }

    private fun initSpinner() {
        val spinnerLeft = listOf(
            AppConstants.ENGLISH,
            AppConstants.CHINA,
            AppConstants.ARABIC,
            AppConstants.GERMAN,
            AppConstants.SPANISH,
            AppConstants.FRENCH,
            AppConstants.HEBREW,
            AppConstants.ITALIAN,
            AppConstants.DUTCH,
            AppConstants.NORWEGIAN,
            AppConstants.PORTUGUESE,
            AppConstants.RUSSIAN
        )
        val arrayAdapterLeft = SpinnerAdapter(this@SearchActivity, spinnerLeft)
        spSearch.adapter = arrayAdapterLeft
        spSearch.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                getDataByLanguageOfItemSpinner()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }
    }

    private fun getDataByLanguageOfItemSpinner() {
        when (spSearch.selectedItem.toString()) {
            AppConstants.ENGLISH -> {
                getDataByCountryISO_639_1(AppConstants.ISO_639_1_ENGLISH)
            }
            AppConstants.CHINA -> {
                getDataByCountryISO_639_1(AppConstants.ISO_639_1_CHINA)
            }
            AppConstants.ARABIC -> {
                getDataByCountryISO_639_1(AppConstants.ISO_639_1_ARABIC)
            }
            AppConstants.GERMAN -> {
                getDataByCountryISO_639_1(AppConstants.ISO_639_1_GERMAN)
            }
            AppConstants.SPANISH -> {
                getDataByCountryISO_639_1(AppConstants.ISO_639_1_SPANISH)
            }
            AppConstants.FRENCH -> {
                getDataByCountryISO_639_1(AppConstants.ISO_639_1_FRENCH)
            }
            AppConstants.HEBREW -> {
                getDataByCountryISO_639_1(AppConstants.ISO_639_1_HEBREW)
            }
            AppConstants.ITALIAN -> {
                getDataByCountryISO_639_1(AppConstants.ISO_639_1_ITALIAN)
            }
            AppConstants.DUTCH -> {
                getDataByCountryISO_639_1(AppConstants.ISO_639_1_DUTCH)
            }
            AppConstants.NORWEGIAN -> {
                getDataByCountryISO_639_1(AppConstants.ISO_639_1_NORWEGIAN)
            }
            AppConstants.PORTUGUESE -> {
                getDataByCountryISO_639_1(AppConstants.ISO_639_1_PORTUGUESE)
            }
            AppConstants.RUSSIAN -> {
                getDataByCountryISO_639_1(AppConstants.ISO_639_1_RUSSIAN)
            }
        }
    }

    private fun getDataByCountryISO_639_1(languageISO_639_1: String) {
        mSearchNewsListAdapter.clearData()
        page = 1
        language = languageISO_639_1
        getNewsListOnLanguage(searchToolbar.etToolbar.text.toString().trim())
    }

    private fun showFragmentDialog(description: String, content: String, url: String) {
        val dialog = DescriptionDiaLogFragment()
        blurSearchLayout.visibility = View.VISIBLE
        mActivityViewModel.isTouching.postValue(true)
        val args = Bundle()
        args.putString(DescriptionDiaLogFragment.KEY_DESCRIPTION, description)
        args.putString(DescriptionDiaLogFragment.KEY_CONTENT, content)
        args.putString(DescriptionDiaLogFragment.KEY_URL, url)
        dialog.arguments = args
        dialog.show(supportFragmentManager.beginTransaction(), "Search Fragment")
    }

    private fun getMoreSearchNews() {
        searchProgressBar.visibility = View.VISIBLE
        Handler().postDelayed({
            getNewsListOnLanguage(searchToolbar.etToolbar.text.toString())
            searchProgressBar.visibility = View.GONE
            mSearchNews.isLoading = true
        }, AppConstants.TIME_DELAY)
    }

    private fun initToolBar() {
        searchToolbar.etToolbar.visibility = View.VISIBLE
        searchToolbar.ivRight.visibility = View.GONE
        searchToolbar.ivLeft.setImageResource(R.drawable.ic_backpress)
    }

    private fun initActionClick() {
        searchToolbar.ivLeft.setOnClickListener {
            onBackPressed()
        }
    }
}