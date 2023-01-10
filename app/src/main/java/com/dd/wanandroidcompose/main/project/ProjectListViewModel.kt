package com.dd.wanandroidcompose.main.project

import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.paging.PagingData
import com.dd.base.base.BaseViewModel
import com.dd.base.paging.simplePager
import com.dd.base.utils.log.LogUtils
import com.dd.wanandroidcompose.API
import com.dd.wanandroidcompose.bean.home.ListWrapper
import com.dd.wanandroidcompose.bean.project.CategoryDetails
import com.dd.wanandroidcompose.main.wechat.WeChatListViewState
import com.dd.wanandroidcompose.net.RxHttpUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor() : BaseViewModel() {

    var viewStates by mutableStateOf(ProjectListViewState())
        private set

    @OptIn(ExperimentalFoundationApi::class)
    fun projectList(cid: Int) {
        viewStates = viewStates.copy(data = simplePager {
            RxHttpUtils.getAwait<ListWrapper<CategoryDetails>>(
                API.Project.projectList(it),
                mapOf("cid" to cid)
            )!!.datas
        })
    }
}

typealias PagingProject = Flow<PagingData<CategoryDetails>>

data class ProjectListViewState @OptIn(ExperimentalFoundationApi::class) constructor(
    val data: PagingProject? = null,
    val listState: LazyStaggeredGridState = LazyStaggeredGridState(),
)