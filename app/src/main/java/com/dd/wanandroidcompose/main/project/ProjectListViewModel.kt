package com.dd.wanandroidcompose.main.project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
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
class ProjectListViewModel @Inject constructor() :
    BaseViewModel() {

    fun projectList(cid :Int): PagingProject = simplePager {
        RxHttpUtils.getCAwait<ListWrapper<CategoryDetails>>(
            API.Project.projectList(it),
            mapOf("cid" to cid)
        )
    }

}

typealias PagingProject = Flow<PagingData<CategoryDetails>>
