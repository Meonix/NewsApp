package com.mionix.newsapp.ui.popular.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mionix.newsapp.R
import com.mionix.newsapp.model.Articles
import com.mionix.newsapp.model.Comment
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.item_popular_news.view.*
import kotlinx.android.synthetic.main.item_popular_news.view.ivItemPopularNews
import java.text.SimpleDateFormat

class CommentAdapter(private var commentList:MutableList<Comment>): RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    inner class CommentViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        fun onBind(data : Comment){
            loadDataIntoRecycleView(data)
        }
        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        private fun loadDataIntoRecycleView(data : Comment) {
            Glide.with(itemView.context)
                .load(data.avatar.replace("Dot","."))
                .centerCrop()
                .placeholder(R.drawable.ic_no_image)
                .into(itemView.ivComment)
            itemView.tvName.text = data.name
            itemView.tvComment.text = data.message
        }
    }
    fun updateData(commentList:MutableList<Comment>){
        this.commentList.addAll(commentList)
        notifyDataSetChanged()
    }
    fun insertData(comment:Comment){
        this.commentList.add(comment)
        notifyItemInserted(this.commentList.size-1)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.onBind(commentList[position])
    }

    override fun getItemCount(): Int {
        return commentList.size
    }
}