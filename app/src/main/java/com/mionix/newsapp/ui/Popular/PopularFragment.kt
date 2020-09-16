package com.mionix.newsapp.ui.Popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mionix.newsapp.R
import com.mionix.newsapp.ui.Popular.adapter.PopularNewsListAdapter
import com.mionix.newsapp.viewmodel.PopularNewsViewModel

import kotlinx.android.synthetic.main.fragment_popular.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class PopularFragment : Fragment() {
    private val mPopularNews : PopularNewsViewModel by viewModel()
    private var mPopularNewsListAdapter = PopularNewsListAdapter(listOf())
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

    override fun onStart() {
        super.onStart()

    }

    override fun onStop() {
        super.onStop()
        blurLayout.pauseBlur()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initRecycleView()
    }

    private fun initRecycleView() {
        mPopularNews.getListPopularNews(1)
        mPopularNews.getListPopularNews.observe(viewLifecycleOwner, Observer {
            mPopularNewsListAdapter.updateData(it.articles)
        })

        rvPopular.adapter = mPopularNewsListAdapter
        rvPopular.layoutManager = LinearLayoutManager(context)
        mPopularNewsListAdapter.onItemClick = {
            val dialog = DescriptionDiaLogFragment()
            fragmentManager?.beginTransaction()?.let { dialog.show(it,"Fragment Dialog") }

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
