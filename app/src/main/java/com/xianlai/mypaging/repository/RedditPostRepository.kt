package com.xianlai.mypaging.repository

import com.xianlai.mypaging.vo.RedditPost

interface RedditPostRepository {
    fun postOfSubreddit(subReddit:String,pageSize:Int):Listing<RedditPost>
}