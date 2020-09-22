package com.mionix.newsapp.ui.Popular

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mionix.newsapp.R
import com.mionix.newsapp.ui.Popular.adapter.PopularNewsListAdapter
import com.mionix.newsapp.ui.Popular.adapter.SpinnerAdapter
import com.mionix.newsapp.ui.viewmodel.ActivityViewModel
import com.mionix.newsapp.ui.viewmodel.PopularNewsViewModel

import kotlinx.android.synthetic.main.fragment_popular.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class PopularFragment : Fragment() {
    private val mPopularNews: PopularNewsViewModel by viewModel()
    private var mPopularNewsListAdapter = PopularNewsListAdapter(mutableListOf())
    private var mActivityViewModel = ActivityViewModel()
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
        const val TIME_DELAY: Long = 1200
        const val ARGENTINA = "Argentina"
        const val UNITED_ARAB_EMIRATES = "United Arab Emirates"
        const val AUSTRIA = "Austria"
        const val AUSTRALIA = "Australia"
        const val BELGIUM = "Belgium"
        const val BULGARIA = "Bulgaria"
        const val BRAZIL = "Brazil"
        const val CANADA = "Canada"
        const val SWITZERLAND = "Switzerland"
        const val CHINA = "China"
        const val COLUMBIA = "Colombia"
        const val CUBA = "Cuba"
        const val CZECHIA = "Czechia"
        const val GERMANY = "Germany"
        const val EGYPT = "Egypt"
        const val FRANCE = "France"
        const val UK_OF_GB_AND_NI = "United Kingdom of Great Britain and Northern Ireland"
        const val GREECE = "Greece"
        const val HONG_KONG = "Hong Kong"
        const val HUNGARY = "Hungary"
        const val INDONESIA = "Indonesia"
        const val IRELAND = "Ireland"
        const val ISRAEL = "Israel"
        const val INDIA = "India"
        const val ITALY = "Italy"
        const val JAPAN = "Japan"
        const val KOREA = "Korea"
        const val LITHUANIA = "Lithuania"
        const val LATVIA = "Latvia"
        const val MOROCCO = "Morocco"
        const val MEXICO = "Mexico"
        const val MALAYSIA = "Malaysia"
        const val NIGERIA = "Nigeria"
        const val NEW_ZEALAND = "New Zealand"
        const val PHILIPPINES = "Philippines"

        const val ISO_3166_1_ARGENTINA = "ar"
        const val ISO_3166_1_UNITED_ARAB_EMIRATES = "ae"
        const val ISO_3166_1_AUSTRIA = "at"
        const val ISO_3166_1_AUSTRALIA = "au"
        const val ISO_3166_1_BELGIUM = "be"
        const val ISO_3166_1_BULGARIA = "bg"
        const val ISO_3166_1_BRAZIL = "br"
        const val ISO_3166_1_CANADA = "ca"
        const val ISO_3166_1_SWITZERLAND = "ch"
        const val ISO_3166_1_CHINA = "cn"
        const val ISO_3166_1_COLUMBIA = "co"
        const val ISO_3166_1_CUBA = "cu"
        const val ISO_3166_1_CZECHIA = "cz"
        const val ISO_3166_1_GERMANY = "de"
        const val ISO_3166_1_EGYPT = "eg"
        const val ISO_3166_1_FRANCE = "fr"
        const val ISO_3166_1_UK_OF_GB_AND_NI = "gb"
        const val ISO_3166_1_GREECE = "gr"
        const val ISO_3166_1_HONG_KONG = "hk"
        const val ISO_3166_1_HUNGARY = "hu"
        const val ISO_3166_1_INDONESIA = "id"
        const val ISO_3166_1_IRELAND = "ie"
        const val ISO_3166_1_ISRAEL = "il"
        const val ISO_3166_1_INDIA = "in"
        const val ISO_3166_1_ITALY = "it"
        const val ISO_3166_1_JAPAN = "jp"
        const val ISO_3166_1_KOREA = "kr"
        const val ISO_3166_1_LITHUANIA = "lt"
        const val ISO_3166_1_LATVIA = "lv"
        const val ISO_3166_1_MOROCCO = "ma"
        const val ISO_3166_1_MEXICO = "mx"
        const val ISO_3166_1_MALAYSIA = "my"
        const val ISO_3166_1_NIGERIA = "ng"
        const val ISO_3166_1_NEW_ZEALAND = "nz"
        const val ISO_3166_1_PHILIPPINES = "ph"
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

    }

    private fun initViewModel() {
        activity?.let {
            mActivityViewModel = ViewModelProviders.of(it).get(ActivityViewModel::class.java)
        }
        getNewsListOnCountry()
        mPopularNews.getListPopularNews.observe(viewLifecycleOwner, Observer {
            mPopularNewsListAdapter.updateData(it.articles)
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
                when (spMain.selectedItem.toString()){
                    ARGENTINA ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_ARGENTINA)
                    }
                    UNITED_ARAB_EMIRATES ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_UNITED_ARAB_EMIRATES)
                    }
                    AUSTRIA ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_AUSTRIA)
                    }
                    AUSTRALIA ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_AUSTRALIA)
                    }
                    BELGIUM ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_BELGIUM)
                    }
                    BULGARIA ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_BULGARIA)
                    }
                    BRAZIL ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_BRAZIL)
                    }
                    CANADA ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_CANADA)
                    }
                    SWITZERLAND ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_SWITZERLAND)
                    }
                    CHINA ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_CHINA)
                    }
                    COLUMBIA ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_COLUMBIA)
                    }
                    CUBA ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_CUBA)
                    }
                    CZECHIA ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_CZECHIA)
                    }
                    GERMANY ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_GERMANY)
                    }
                    EGYPT ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_EGYPT)
                    }
                    FRANCE ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_FRANCE)
                    }
                    UK_OF_GB_AND_NI ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_UK_OF_GB_AND_NI)
                    }
                    GREECE ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_GREECE)
                    }
                    HONG_KONG ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_HONG_KONG)
                    }
                    HUNGARY ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_HUNGARY)
                    }
                    INDONESIA ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_INDONESIA)
                    }
                    IRELAND ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_IRELAND)
                    }
                    ISRAEL ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_ISRAEL)
                    }
                    INDIA ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_INDIA)
                    }
                    ITALY ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_ITALY)
                    }
                    JAPAN ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_JAPAN)
                    }
                    KOREA ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_KOREA)
                    }
                    LITHUANIA ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_LITHUANIA)
                    }
                    LATVIA ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_LATVIA)
                    }
                    MOROCCO ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_MOROCCO)
                    }
                    MEXICO ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_MEXICO)
                    }
                    MALAYSIA ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_MALAYSIA)
                    }
                    NIGERIA ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_NIGERIA)
                    }
                    NEW_ZEALAND ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_NEW_ZEALAND)
                    }
                    PHILIPPINES ->{
                        getDataByCountryISO_3166_1(ISO_3166_1_PHILIPPINES)
                    }
                }
            }
            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }
        }
    }
    private fun getDataByCountryISO_3166_1(countryISO_3166_1:String){
        mPopularNewsListAdapter.clearData()
        page = 1
        country = countryISO_3166_1
        getNewsListOnCountry()
    }
    private fun getNewsListOnCountry(){
        mPopularNews.getListPopularNews(page,country)
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
    }

    private fun showFragmentDialog(description: String, content: String, url: String) {
        val dialog = DescriptionDiaLogFragment()
        mActivityViewModel.isTouching.postValue(true)
        val args = Bundle()
        args.putString(DescriptionDiaLogFragment.KEY_DESCRIPTION, description)
        args.putString(DescriptionDiaLogFragment.KEY_CONTENT, content)
        args.putString(DescriptionDiaLogFragment.KEY_URL, url)
        dialog.arguments = args
        fragmentManager?.beginTransaction()?.let { ft ->
            dialog.show(ft, "Fragment Dialog")
        }
    }


}
