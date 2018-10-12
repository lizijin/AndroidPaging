package com.xianlai.mypaging.repository

import androidx.annotation.MainThread
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.xianlai.mypaging.api.RedditApi
import com.xianlai.mypaging.vo.RedditPost
import java.util.concurrent.Executor

class InMemoryByPageKeyRepository(private val redditApi: RedditApi,
                                  private val networkExecutor: Executor) : RedditPostRepository {
    @MainThread
    override fun postOfSubreddit(subReddit: String, pageSize: Int): Listing<RedditPost> {
        val sourceFactory = RedditDataSourceFactory(redditApi, subReddit, networkExecutor)

        val livePagedList = LivePagedListBuilder(sourceFactory, pageSize)
                // provide custom executor for network requests, otherwise it will default to
                // Arch Components' IO pool which is also used for disk access
                .setFetchExecutor(networkExecutor)
                .build()

        return Listing(
                pagedList = livePagedList

        )
    }
}