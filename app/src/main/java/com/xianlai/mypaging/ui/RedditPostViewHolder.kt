package com.xianlai.mypaging.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xianlai.mypaging.R
import com.xianlai.mypaging.vo.RedditPost

class RedditPostViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
    private val title: TextView = itemView.findViewById(R.id.title)
    private val subtitle: TextView = itemView.findViewById(R.id.subtitle)
    private val score: TextView = itemView.findViewById(R.id.score)
    private val thumbnail : ImageView = itemView.findViewById(R.id.thumbnail)
    private var post : RedditPost? = null

    init {
        itemView.setOnClickListener {
            post?.url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                itemView.context.startActivity(intent)
            }
        }
    }

    fun bind(post: RedditPost?) {
        this.post = post
        title.text = post?.title ?: "loading"
        subtitle.text = itemView.context.resources.getString(R.string.post_subtitle,
                post?.author ?: "unknown")
        score.text = "${post?.score ?: 0}"
//        if (post?.thumbnail?.startsWith("http") == true) {
//            thumbnail.visibility = View.VISIBLE
//            glide.load(post.thumbnail)
//                    .centerCrop()
//                    .placeholder(R.drawable.ic_insert_photo_black_48dp)
//                    .into(thumbnail)
//        } else {
//            thumbnail.visibility = View.GONE
//            glide.clear(thumbnail)
//        }
    }

    companion object {
        fun create(parent: ViewGroup): RedditPostViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.reddit_post_item, parent, false)
            return RedditPostViewHolder(view)
        }
    }

    fun updateScore(item: RedditPost?) {
        post = item
        score.text = "${item?.score ?: 0}"
    }
}