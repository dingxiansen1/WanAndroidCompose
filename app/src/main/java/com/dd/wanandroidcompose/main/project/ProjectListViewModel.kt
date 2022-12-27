package com.dd.wanandroidcompose.main.project

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

/*@HiltViewModel
class ProjectListViewModel @Inject constructor() : BaseViewModel() {*/
class ProjectListViewModel constructor(cid: Int) : BaseViewModel() {

    private val pager by lazy {
        simplePager {
            RxHttpUtils.getAwait<ListWrapper<CategoryDetails>>(
                API.Project.projectList(it), mapOf("cid" to cid)
            )!!.datas
        }
    }
    var viewStates by mutableStateOf(ProjectListViewState(data = pager))
        private set

    /* fun projectList(cid :Int): PagingProject = simplePager {
         RxHttpUtils.getAwait<ListWrapper<CategoryDetails>>(
             API.Project.projectList(it),
             mapOf("cid" to cid)
         )!!.datas
     }*/

}

typealias PagingProject = Flow<PagingData<CategoryDetails>>

data class ProjectListViewState(
    val data: PagingProject,
)