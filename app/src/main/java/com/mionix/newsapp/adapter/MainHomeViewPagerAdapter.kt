package com.mionix.newsapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.mionix.newsapp.ui.Popular.PopularFragment

class MainHomeViewPagerAdapter(
    private val listTitle: MutableList<String>,
    manager: FragmentManager
) : FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (listTitle[position]) {
            "Popular" -> PopularFragment.newInstance()
            //"Register Calendar" -> RegisterCalFragment.newInstance()
           /* 5 -> UsefulInfoFragment.newInstance()*/
            else -> PopularFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return listTitle.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return listTitle[position]
    }

}

