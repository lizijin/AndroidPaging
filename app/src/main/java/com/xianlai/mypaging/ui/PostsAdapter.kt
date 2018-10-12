package com.xianlai.mypaging.ui

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.xianlai.mypaging.vo.RedditPost

class PostsAdapter :PagedListAdapter<RedditPost,RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<RedditPost>() {
    override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean =
            oldItem == newItem

    override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean =
            oldItem.name == newItem.name}){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return RedditPostViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
     if(holder is RedditPostViewHolder) holder.bind(getItem(position))
    }

}