package com.mionix.newsapp.ui.Popular.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
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

class PopularNewsListAdapter(private var newsList:MutableList<Articles>): RecyclerView.Adapter<PopularNewsListAdapter.PopularNewList>() {
    var onItemLongClick: ((description:String,content:String,url:String) -> Unit)? = null
    var onItemTouchClick: ((view:View , motionEvent:MotionEvent) -> Unit)? = null

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
    fun clearData(){
        this.newsList.clear()
        notifyDataSetChanged()
    }
    fun updateData(newsList:List<Articles>){
        this.newsList.addAll(newsList)
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: PopularNewsListAdapter.PopularNewList, position: Int) {
        holder.onBind(newsList[position])
    }
    inner class PopularNewList(itemView :View): RecyclerView.ViewHolder(itemView){
        @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
        fun onBind(data : Articles){
            //Load image to image view
            Glide.with(itemView.context)
                .load(data.urlToImage)
                .centerCrop()
                .placeholder(R.drawable.ic_error_404)
                .into(itemView.ivItemPopularNews)
            itemView.tvItemTitlePopularNews.text = data.title
            itemView.tvItemSourceAndTimePopularNews.text = "${data.publishedAt} | ${data.source.name}"
            itemView.setOnLongClickListener {
                onItemLongClick?.invoke(data.description ?: "", data.content ?: "",data.urlToImage?:"")
                return@setOnLongClickListener true
            }
            itemView.setOnTouchListener { view, motionEvent ->
                onItemTouchClick?.invoke(view,motionEvent)
                return@setOnTouchListener false
            }
        }
    }
}