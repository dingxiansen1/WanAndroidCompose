package com.dd.wanandroidcompose.main.project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.dd.base.base.BaseViewModel
import com.dd.base.ext.launch
import com.dd.wanandroidcompose.API
import com.dd.wanandroidcompose.bean.project.Category
import com.dd.wanandroidcompose.main.home.HomeViewState
import com.dd.wanandroidcompose.net.RxHttpUtils

class ProjectViewModel : BaseViewModel() {

    var viewStates by mutableStateOf(ProjectViewState())
        private set

    init {
        getCategory()
    }

    private fun getCategory() {
        launch {

            val list = RxHttpUtils.getAwait<List<Category>>(API.Project.Category) ?: emptyList()
            if (list.isNotEmpty()) {
                //缓存在本地
            }
            viewStates = ProjectViewState(list)
        }
    }

    fun getCategoryDetails() {
        launch {

            val list = RxHttpUtils.getAwait<List>(API.Project.Category) ?: emptyList()
            if (list.isNotEmpty()) {
                //缓存在本地
            }
            viewStates = ProjectViewState(list)
        }
    }
}

data class ProjectViewState(
    val category: List<Category> = emptyList()
)