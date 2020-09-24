package com.mionix.newsapp.ui.Search.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mionix.newsapp.R
import com.mionix.newsapp.model.Articles
import kotlinx.android.synthetic.main.item_popular_news.view.*

class ListSearchAdapter(private val listResult:List<Articles>):RecyclerView.Adapter<ListSearchAdapter.ListSearchViewHolder>() {
    var onItemLongClick: ((description:String,content:String,url:String) -> Unit)? = null
    var onItemTouchClick: ((view:View , motionEvent: MotionEvent) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListSearchAdapter.ListSearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_popular_news,parent,false)
        return ListSearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listResult.size
    }

    override fun onBindViewHolder(holder: ListSearchViewHolder, position: Int) {
        holder.onBind(listResult[position])
    }
    inner class ListSearchViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView){
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