package com.dd.wanandroidcompose.main.project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.paging.PagingData
import com.dd.base.base.BaseViewModel
import com.dd.base.paging.ListWrapper
import com.dd.base.paging.simplePager
import com.dd.wanandroidcompose.API
import com.dd.wanandroidcompose.bean.project.CategoryDetails
import com.dd.wanandroidcompose.net.RxHttpUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor(private val cid: Int) : BaseViewModel() {

    private val pager by lazy {
        simplePager {
            RxHttpUtils.getCAwait<ListWrapper<CategoryDetails>>(API.Project.projectList(it), mapOf("cid" to cid))
        }
    }
    var viewStates by mutableStateOf(ProjectListViewState(pager))
        private set


}

typealias PagingProject= Flow<PagingData<CategoryDetails>>

data class ProjectListViewState(
    val projectList: PagingProject
)