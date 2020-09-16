package com.mionix.newsapp.ui.Popular.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mionix.newsapp.R
import com.mionix.newsapp.model.Articles
import com.mionix.newsapp.model.ListPopularNews
import com.mionix.newsapp.ui.Popular.DescriptionDiaLogFragment
import kotlinx.android.synthetic.main.item_popular_news.view.*
import kotlinx.android.synthetic.main.layout_toolbar_view.view.*

class PopularNewsListAdapter(private var newsList:List<Articles>): RecyclerView.Adapter<PopularNewsListAdapter.PopularNewList>() {
    var onItemClick: (() -> Unit)? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularNewsListAdapter.PopularNewList {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_popular_news, parent, false)
        return PopularNewList(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
    fun updateData(newsList:List<Articles>){
        this.newsList = newsList
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: PopularNewsListAdapter.PopularNewList, position: Int) {
        holder.onBind(newsList[position])
    }
    inner class PopularNewList(itemView :View): RecyclerView.ViewHolder(itemView){
        @SuppressLint("SetTextI18n")
        fun onBind(data : Articles){
            Glide.with(itemView.context)
                .load(data.urlToImage)
                .centerCrop()
                .placeholder(R.drawable.ic_error_404)
                .into(itemView.ivItemPopularNews)
            itemView.tvItemTitlePopularNews.text = data.title
            itemView.tvItemSourceAndTimePopularNews.text = "${data.publishedAt} | ${data.source.name}"
            itemView.setOnClickListener {
                onItemClick?.invoke()
            }
        }
    }
}