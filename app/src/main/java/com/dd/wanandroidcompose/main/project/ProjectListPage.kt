package com.dd.wanandroidcompose.main.project

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    val viewModel: ProjectListViewModel = hiltViewModel()
    val listData = viewModel.projectList(cid).collectAsLazyPagingItems()
    LazyVerticalStaggeredGrid(
        modifier = Modifier.fillMaxSize(),
        //Item列数
        columns = StaggeredGridCells.Fixed(2),
        // 整体内边距
        contentPadding = PaddingValues(0.dp, 0.dp),
        // item 和 item 之间的纵向间距
        verticalArrangement = Arrangement.spacedBy(0.dp),
        // item 和 item 之间的横向间距
        horizontalArrangement = Arrangement.spacedBy(0.dp),

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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            AsyncImage(model = item.envelopePic, contentDescription = "图片")
            Text(
                text = item.desc,
                style = TextStyle(fontSize = 12.sp, color = AppTheme.colors.textPrimary),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}