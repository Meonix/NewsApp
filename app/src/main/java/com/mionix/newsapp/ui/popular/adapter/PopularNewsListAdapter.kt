package com.mionix.newsapp.ui.popular.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mionix.newsapp.R
import com.mionix.newsapp.model.Articles
import kotlinx.android.synthetic.main.item_popular_news.view.*
import java.text.SimpleDateFormat

class PopularNewsListAdapter(private var newsList:MutableList<Articles>): RecyclerView.Adapter<PopularNewsListAdapter.PopularNewList>() {
    var onItemLongClick: ((description:String,content:String,url:String) -> Unit)? = null
    var onItemTouchClick: ((view:View , motionEvent:MotionEvent) -> Unit)? = null
    var onItemClick: ((urlWebView:String) -> Unit)? = null

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
        val totalItem = itemCount
        this.newsList.addAll(newsList)
        notifyItemRangeInserted(totalItem,newsList.size -1)
    }
    override fun onBindViewHolder(holder: PopularNewsListAdapter.PopularNewList, position: Int) {
        holder.onBind(newsList[position])
    }
    inner class PopularNewList(itemView :View): RecyclerView.ViewHolder(itemView){
        fun onBind(data : Articles){
            //Load image to image view
            loadDataIntoView(data)
            handleActionClick(data)
        }

        @SuppressLint("ClickableViewAccessibility")
        private fun handleActionClick(data : Articles) {
            itemView.setOnLongClickListener {
                onItemLongClick?.invoke(data.description ?: "", data.content ?: "",data.urlToImage?:"")
                return@setOnLongClickListener true
            }
            itemView.setOnTouchListener { view, motionEvent ->
                onItemTouchClick?.invoke(view,motionEvent)
                return@setOnTouchListener false
            }
            itemView.setOnClickListener {
                onItemClick?.invoke(data.url)
            }
        }

        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        private fun loadDataIntoView(data : Articles) {
            Glide.with(itemView.context)
                .load(data.urlToImage)
                .centerCrop()
                .placeholder(R.drawable.ic_no_image)
                .into(itemView.ivItemPopularNews)
            itemView.tvItemTitlePopularNews.text = data.title
            // format date time
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
            val output: String = formatter.format(parser.parse(data.publishedAt))
            itemView.tvItemSourceAndTimePopularNews.text = "$output | ${data.source.name}"
        }
    }
}