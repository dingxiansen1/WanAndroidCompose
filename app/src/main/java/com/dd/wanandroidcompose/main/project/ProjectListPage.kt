package com.dd.wanandroidcompose.main.project

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.dd.base.theme.AppTheme
import com.dd.wanandroidcompose.bean.project.CategoryDetails
import com.dd.wanandroidcompose.main.wechat.WeChatListViewModel
import com.dd.wanandroidcompose.navigator.RouteName

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProjectListPage(
    cid: Int, navCtrl: NavHostController
) {
   /*  val viewModel: ProjectListViewModel = hiltViewModel()
    val listData = viewModel.projectList(cid).collectAsLazyPagingItems()  */
    val viewModel: ProjectListViewModel = viewModel(key = cid.toString(),factory = remember {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ProjectListViewModel(cid) as T
            }
        }
    })
    val listData = viewModel.viewStates.data.collectAsLazyPagingItems()
    LazyVerticalStaggeredGrid(
        modifier = Modifier.fillMaxSize(),
        //Item列数
        columns = StaggeredGridCells.Fixed(1),
        // 整体内边距
        contentPadding = PaddingValues(0.dp, 0.dp),
        // item 和 item 之间的纵向间距
        verticalArrangement = Arrangement.spacedBy(0.dp),
        // item 和 item 之间的横向间距
        horizontalArrangement = Arrangement.spacedBy(10.dp),

        content = {
            items(listData.itemCount) { index ->
                listData[index]?.let {
                    ProjectListItem(it,navCtrl)
                }
            }
        })
}

@Composable
fun ProjectListItem(data: CategoryDetails,navCtrl: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navCtrl.navigate("${RouteName.Web}?link=${data.link}&title=${data.title}")
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            AsyncImage(
                model = data.envelopePic,
                contentDescription = "图片",
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxHeight()
                    .width(60.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),

                ) {
                Text(
                    text = data.title,
                    style = TextStyle(fontSize = 12.sp, color = AppTheme.colors.textPrimary),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = data.desc,
                    style = TextStyle(fontSize = 10.sp, color = AppTheme.colors.textPrimary),
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
            color = AppTheme.colors.divider
        )
    }

}