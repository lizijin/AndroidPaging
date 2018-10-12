package com.xianlai.mypaging

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.xianlai.mypaging.api.RedditApi
import com.xianlai.mypaging.repository.InMemoryByPageKeyRepository
import com.xianlai.mypaging.repository.RedditPostRepository
import com.xianlai.mypaging.ui.PostsAdapter
import com.xianlai.mypaging.viewmodel.RedditViewModel
import com.xianlai.mypaging.vo.RedditPost
import kotlinx.android.synthetic.main.activity_paging.*
import java.util.concurrent.Executors

class PagingActivity : AppCompatActivity() {
    companion object {
        const val KEY_SUBREDDIT = "subreddit"
        const val DEFAULT_SUBREDDIT = "androiddev"
        const val KEY_REPOSITORY_TYPE = "repository_type"
//        fun intentFor(context: Context, type: RedditPostRepository.Type): Intent {
//            val intent = Intent(context, RedditActivity::class.java)
//            intent.putExtra(KEY_REPOSITORY_TYPE, type.ordinal)
//            return intent
//        }
    }

    private lateinit var model: RedditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging)

        model = getViewModel()
        initAdapter()
        initSwipeToRefresh()
        initSearch()
//        val subreddit = savedInstanceState?.getString(KEY_SUBREDDIT) ?: DEFAULT_SUBREDDIT
        model.showSubreddit("androiddev")
    }

    private fun getViewModel(): RedditViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repository = InMemoryByPageKeyRepository(api, Executors.newFixedThreadPool(5))
                @Suppress("UNCHECKED_CAST")
                return RedditViewModel(repository) as T
            }
        })[RedditViewModel::class.java]
    }

    private val api by lazy {
        RedditApi.create()
    }

    private fun initAdapter() {
        val adapter = PostsAdapter()
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)
        model.posts.observe(this, Observer<PagedList<RedditPost>> {
            adapter.submitList(it)
        })
//        model.networkState.observe(this, Observer {
//            adapter.setNetworkState(it)
//        })
    }

    private fun initSwipeToRefresh() {
//        model.refreshState.observe(this, Observer {
//            swipe_refresh.isRefreshing = it == NetworkState.LOADING
//        })
//        swipe_refresh.setOnRefreshListener {
//            model.refresh()
//        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_SUBREDDIT, model.currentSubreddit())
    }

    private fun initSearch() {
        input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updatedSubredditFromInput()
                true
            } else {
                false
            }
        }
        input.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updatedSubredditFromInput()
                true
            } else {
                false
            }
        }
    }

    private fun updatedSubredditFromInput() {
        input.text.trim().toString().let {
            if (it.isNotEmpty()) {
                if (model.showSubreddit(it)) {
                    list.scrollToPosition(0)
                    (list.adapter as? PostsAdapter)?.submitList(null)
                }
            }
        }
    }
}
