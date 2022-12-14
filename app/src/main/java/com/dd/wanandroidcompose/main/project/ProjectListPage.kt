package com.dd.wanandroidcompose.main.project

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.dd.base.theme.AppTheme
import com.dd.wanandroidcompose.bean.project.CategoryDetails

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProjectListPage(
    cid: Int
) {
    val viewModel:ProjectListViewModel = hiltViewModel()
    val listData = viewModel.projectList(cid).collectAsLazyPagingItems()
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
            items(listData.itemCount) { index ->
                listData[index]?.let {
                    ProjectListItem(it)
                }
            }
        })
}

@Composable
fun ProjectListItem(item: CategoryDetails) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            AsyncImage(model = item.envelopePic, contentDescription = "图片")
            Text(
                text = item.desc,
                style = TextStyle(fontSize = 16.sp, color = AppTheme.colors.textPrimary),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}