package com.mionix.newsapp.ui.sources

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mionix.newsapp.R


class SourcesFragment : Fragment() {

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

    companion object {
        fun newInstance(): SourcesFragment {
            val args = Bundle()
            val fragment = SourcesFragment()
            fragment.arguments = args
            return fragment
        }
    }
}