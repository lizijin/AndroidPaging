package com.xianlai.mypaging.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class Listing<T> (
        val pagedList:LiveData<PagedList<T>>
)