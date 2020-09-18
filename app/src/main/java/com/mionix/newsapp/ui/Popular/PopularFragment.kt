package com.mionix.newsapp.ui.Popular

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mionix.newsapp.R
import com.mionix.newsapp.ui.Popular.adapter.PopularNewsListAdapter
import com.mionix.newsapp.ui.viewmodel.ActivityViewModel
import com.mionix.newsapp.ui.viewmodel.PopularNewsViewModel

import kotlinx.android.synthetic.main.fragment_popular.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class PopularFragment : Fragment() {
    private val mPopularNews : PopularNewsViewModel by viewModel()
    private var mPopularNewsListAdapter = PopularNewsListAdapter(listOf())
    private var mActivityViewModel = ActivityViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        initRecycleView()
    }

    private fun initViewModel() {
        activity?.let {
            mActivityViewModel = ViewModelProviders.of(it).get(ActivityViewModel::class.java)
        }
        mPopularNews.getListPopularNews(1)
        mPopularNews.getListPopularNews.observe(viewLifecycleOwner, Observer {
            mPopularNewsListAdapter.updateData(it.articles)
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initRecycleView() {
        rvPopular.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    //When scroll Blur view will gone
                    mActivityViewModel.isTouching.postValue(false)
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
        rvPopular.adapter = mPopularNewsListAdapter
        rvPopular.layoutManager = LinearLayoutManager(context)
        handleOnClickItemView()
    }

    private fun handleOnClickItemView() {
        mPopularNewsListAdapter.onItemLongClick = { description,content,url ->
            showFragmentDialog(description,content,url)
        }
        mPopularNewsListAdapter.onItemTouchClick = {view , motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    mActivityViewModel.isTouching.postValue(false)
                }
            }
        }
    }

    private fun showFragmentDialog(description: String, content: String,url :String) {
        val dialog = DescriptionDiaLogFragment()
        mActivityViewModel.isTouching.postValue(true)
        val args = Bundle()
        args.putString(DescriptionDiaLogFragment.KEY_DESCRIPTION, description)
        args.putString(DescriptionDiaLogFragment.KEY_CONTENT, content)
        args.putString(DescriptionDiaLogFragment.KEY_URL,url)
        dialog.arguments = args
        fragmentManager?.beginTransaction()?.let {ft->
            dialog.show(ft,"Fragment Dialog")
        }
    }

    companion object {
        fun newInstance(): PopularFragment {
            val args = Bundle()
            val fragment = PopularFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
