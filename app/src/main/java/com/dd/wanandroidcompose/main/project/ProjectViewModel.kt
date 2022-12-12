package com.dd.wanandroidcompose.main.project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.dd.base.base.BaseViewModel
import com.dd.base.ext.launch
import com.dd.wanandroidcompose.API
import com.dd.wanandroidcompose.bean.project.Category
import com.dd.wanandroidcompose.net.RxHttpUtils
import com.dd.wanandroidcompose.utils.RoomUtils

class ProjectViewModel : BaseViewModel() {

    var viewStates by mutableStateOf(ProjectViewState())
        private set

    init {
        getCategory()
    }

    private fun getCategory() {
        launch {
            val cacheList = RoomUtils.getProjectCategory()
            if (cacheList!=null&& cacheList.isNotEmpty()){
                viewStates = ProjectViewState(cacheList)
                return@launch
            }
            val list = RxHttpUtils.getAwait<List<Category>>(API.Project.Category) ?: emptyList()
            if (list.isNotEmpty()) {
                viewStates = ProjectViewState(list)
                //缓存在本地
                RoomUtils.setProjectCategory(list)
            }
        }
    }
}

data class ProjectViewState(
    val category: List<Category> = emptyList()
)