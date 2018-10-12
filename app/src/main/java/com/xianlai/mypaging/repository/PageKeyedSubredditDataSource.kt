package com.xianlai.mypaging.repository

import androidx.paging.PageKeyedDataSource
import com.xianlai.mypaging.api.RedditApi
import com.xianlai.mypaging.vo.RedditPost
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.Executor

class PageKeyedSubredditDataSource(
        private val redditApi: RedditApi,
        private val subredditName: String,
        private val retryExecutor: Executor
) : PageKeyedDataSource<String,RedditPost>(){
    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, RedditPost>) {
        val request = redditApi.getTop(
                subreddit = subredditName,
                limit = params.requestedLoadSize
        )
        val response = request.execute()
        val data = response.body()?.data
        val items = data?.children?.map { it.data } ?: emptyList()
        callback.onResult(items, data?.before, data?.after)
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {

        redditApi.getTopAfter(subreddit = subredditName,
                after = params.key,
                limit = params.requestedLoadSize).enqueue(
                object : retrofit2.Callback<RedditApi.ListingResponse> {
                    override fun onFailure(call: Call<RedditApi.ListingResponse>, t: Throwable) {
                    }

                    override fun onResponse(
                            call: Call<RedditApi.ListingResponse>,
                            response: Response<RedditApi.ListingResponse>) {
                        if (response.isSuccessful) {
                            val data = response.body()?.data
                            val items = data?.children?.map { it.data } ?: emptyList()
                            callback.onResult(items, data?.after)
                        } else {
//                            retry = {
//                                loadAfter(params, callback)
//                            }
//                            networkState.postValue(
//                                    NetworkState.error("error code: ${response.code()}"))
                        }
                    }
                }
        )
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}