package com.mionix.newsapp.ui.sources.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mionix.newsapp.R
import com.mionix.newsapp.model.AllSource
import com.mionix.newsapp.model.Articles
import com.mionix.newsapp.ui.popular.adapter.PopularNewsListAdapter
import kotlinx.android.synthetic.main.item_popular_news.view.*
import kotlinx.android.synthetic.main.item_sources_news.view.*

class SourcesListAdapter (private var newsList:MutableList<AllSource>): RecyclerView.Adapter<SourcesListAdapter.SourcesNewsList>() {

    var onItemClick: ((view: View, motionEvent: MotionEvent) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SourcesListAdapter.SourcesNewsList {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sources_news, parent, false)
        return SourcesNewsList(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
    fun clearData(){
        this.newsList.clear()
        notifyDataSetChanged()
    }
    fun updateData(newsList:List<AllSource>){
        val totalItem = itemCount
        this.newsList.addAll(newsList)
        notifyItemRangeInserted(totalItem,newsList.size -1 )
    }
    override fun onBindViewHolder(holder: SourcesListAdapter.SourcesNewsList, position: Int) {
        holder.onBind(newsList[position])
    }
    inner class SourcesNewsList(itemView : View): RecyclerView.ViewHolder(itemView){
        @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
        fun onBind(data : AllSource){
            itemView.tvNameSources.text = data.name
            itemView.tvDescriptionSources.text = data.description
//            itemView.setOnTouchListener { view, motionEvent ->
//                onItemClick?.invoke(view,motionEvent)
//                return@setOnTouchListener false
//            }
        }
    }
}