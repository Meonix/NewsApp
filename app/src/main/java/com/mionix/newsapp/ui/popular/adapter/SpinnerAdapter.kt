package com.mionix.newsapp.ui.popular.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mionix.newsapp.R

class SpinnerAdapter(private val context: Context, private var listFilter : List<String>) :BaseAdapter() {
    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemHolder
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (convertView == null) {
            view = inflater.inflate(R.layout.view_filter, parent, false)
            vh = ItemHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.label.text = listFilter[p0]

        return view
    }

    override fun getItem(position: Int): Any {
        return listFilter[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getCount(): Int {
        return listFilter.size
    }
    private class ItemHolder(row: View?) {
        val label: TextView = row?.findViewById(R.id.tvSpinner) as TextView
    }
}