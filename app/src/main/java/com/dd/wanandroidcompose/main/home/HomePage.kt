package com.dd.wanandroidcompose.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.annotation.ExperimentalCoilApi
import com.dd.base.ext.showToast
import com.dd.base.theme.AppTheme
import com.dd.base.widget.Banner
import com.dd.base.widget.SwipeRefreshList
import com.dd.wanandroidcompose.bean.home.HomeBanner
import com.dd.wanandroidcompose.bean.home.HomeData
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomePage(
    navCtrl: NavHostController, scaffoldState: ScaffoldState, scope: CoroutineScope
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val banner = viewModel.viewStates.banner
    val listData = viewModel.viewStates.data.collectAsLazyPagingItems()
    val listState = if (listData.itemCount > 0) viewModel.viewStates.listState else LazyListState()

    Column(
        Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
    ) {
        SwipeRefreshList(
            modifier = Modifier.fillMaxSize(),
            lazyPagingItems = listData,
            listState = listState,
            refresh = {
                viewModel.getBanner()
            },
            itemContent = {
                item {
                    BannerItem(banner)
                }
                itemsIndexed(listData) { _, item ->
                    HomeDataItem(item!!)
                }

            })

    }

}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun BannerItem(banner: List<HomeBanner>) {
    Banner(modifier = Modifier
        .background(AppTheme.colors.background)
        .fillMaxWidth()
        .padding(20.dp, 10.dp)
        .height(150.dp),
        list = banner,
        onClick = { url, title ->
            showToast(title)
        })
}

@Composable
fun HomeDataItem(data: HomeData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp, 5.dp)
            .clickable { },
        elevation = 10.dp // 设置阴影
    ) {
        Column(
          modifier =   Modifier
                .fillMaxSize()
                .padding(5.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = data.author(),
                    style = TextStyle(fontSize = 12.sp, color = AppTheme.colors.textPrimary),
                )
                Text(
                    text = data.niceDate,
                    style = TextStyle(fontSize = 12.sp, color = AppTheme.colors.textPrimary),
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth().weight(3f),
                text = data.title,
                style = TextStyle(fontSize = 16.sp, color = AppTheme.colors.textPrimary),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = data.superChapterName,
                    style = TextStyle(fontSize = 12.sp, color = AppTheme.colors.textPrimary),
                )
                IconButton(onClick = {
                    data.collect = !data.collect
                }) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "收藏",
                        tint = if (data.collect) AppTheme.colors.error else AppTheme.colors.divider
                    )
                }
            }
        }

    }
}


