package com.xianlai.mypaging.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.xianlai.mypaging.api.RedditApi
import com.xianlai.mypaging.vo.RedditPost
import java.util.concurrent.Executor


class RedditDataSourceFactory(
        private val redditApi:RedditApi,
        private val subredditName:String,
        private val retryExecutor:Executor
): DataSource.Factory<String,RedditPost>(){
    val sourceLiveData = MutableLiveData<PageKeyedSubredditDataSource>()
    override fun create(): DataSource<String, RedditPost> {
        val source = PageKeyedSubredditDataSource(redditApi,subredditName,retryExecutor)
        sourceLiveData.postValue(source)
        return source
    }
}