package com.dd.wanandroidcompose.main.project

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dd.base.base.viewModelInstance
import com.dd.wanandroidcompose.bean.project.CategoryDetails

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProjectListPage(cid: Int) {
    val viewModel =  viewModelInstance{
        ProjectListViewModel(cid)
    }
    val list = viewModel.viewStates.projectList.collectAsLazyPagingItems()
    LazyVerticalStaggeredGrid(
        //Item列数
        columns = StaggeredGridCells.Fixed(2),
        // 整体内边距
        contentPadding = PaddingValues(8.dp, 8.dp),
        // item 和 item 之间的纵向间距
        verticalArrangement = Arrangement.spacedBy(4.dp),
        // item 和 item 之间的横向间距
        horizontalArrangement = Arrangement.spacedBy(8.dp),

        content = {

        })
}

@Composable
fun ProjectListItem(item: LazyPagingItems<CategoryDetails>) {
    Card(modifier = Modifier.fillMaxWidth()) {

    }
}