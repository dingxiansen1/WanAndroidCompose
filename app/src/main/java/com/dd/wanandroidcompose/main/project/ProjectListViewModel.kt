package com.dd.wanandroidcompose.main.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import com.dd.base.base.BaseViewModel
import com.dd.base.ext.launch
import com.dd.base.paging.ListWrapper
import com.dd.base.paging.simplePager
import com.dd.wanandroidcompose.API
import com.dd.wanandroidcompose.bean.home.HomeData
import com.dd.wanandroidcompose.bean.project.Category
import com.dd.wanandroidcompose.bean.project.CategoryDetails
import com.dd.wanandroidcompose.net.RxHttpUtils
import com.dd.wanandroidcompose.utils.RoomUtils
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