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
import com.dd.base.widget.SearchBarNotClickable
import com.dd.base.widget.SwipeRefreshList
import com.dd.wanandroidcompose.bean.home.HomeBanner
import com.dd.wanandroidcompose.bean.home.HomeData
import com.dd.wanandroidcompose.navigator.RouteName
import com.dd.wanandroidcompose.search.SearchViewAction
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomePage(
    navCtrl: NavHostController,
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
                listData.refresh()
            },
            itemContent = {
                item {
                    SearchBarNotClickable(hint = "jetpack compose") {
                        navCtrl.navigate(RouteName.Search)
                    }
                    BannerItem(banner) { url, title ->
                        navCtrl.navigate("${RouteName.Web}?link=${url}&title=${title}")
                    }
                }
                itemsIndexed(listData) { index, item ->
                    HomeDataItem(
                        item!!,
                        onClick = {
                            navCtrl.navigate("${RouteName.Web}?link=${item.link}&title=${item.title}")
                        },
                        collectOnClick = {
                            listData[index]?.collect = !item.collect
                        }
                    )
                }

            })

    }

}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun BannerItem(banner: List<HomeBanner>, onClick: ((String, String) -> Unit)) {
    Banner(modifier = Modifier
        .background(AppTheme.colors.background)
        .fillMaxWidth()
        .padding(20.dp, 10.dp)
        .height(150.dp),
        list = banner,
        onClick = { url, title ->
            onClick.invoke(url, title)
        })
}

@Composable
fun HomeDataItem(data: HomeData, onClick: (() -> Unit), collectOnClick: (() -> Unit)) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp, 5.dp)
            .clickable {
                onClick.invoke()
            },
        elevation = 10.dp // 设置阴影
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = data.author(),
                    style = TextStyle(fontSize = 12.sp, color = AppTheme.colors.textPrimary),
                )
                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = data.niceDate,
                    style = TextStyle(fontSize = 12.sp, color = AppTheme.colors.textPrimary),
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f),
                text = data.title,
                style = TextStyle(fontSize = 16.sp, color = AppTheme.colors.textPrimary),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = data.superChapterName,
                    style = TextStyle(fontSize = 12.sp, color = AppTheme.colors.textPrimary),
                )
                IconButton(onClick = {
                    collectOnClick.invoke()
                }) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "收藏",
                        tint = if (data.collect) AppTheme.colors.error else AppTheme.colors.divider,
                    )
                }
            }
        }

    }
}


