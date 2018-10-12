package com.xianlai.mypaging.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.xianlai.mypaging.repository.RedditPostRepository

class RedditViewModel(
        private val respository:RedditPostRepository
):ViewModel(){
    private val subredditName by lazy {  MutableLiveData<String>()
    }
    private val repoResult = Transformations.map(subredditName){
        respository.postOfSubreddit(it,30)
    }
    val posts = Transformations.switchMap(repoResult) { it.pagedList }!!
    fun showSubreddit(subreddit: String): Boolean {
        if (subredditName.value == subreddit) {
            return false
        }
        subredditName.value = subreddit
        return true
    }
    fun currentSubreddit(): String? = subredditName.value

}