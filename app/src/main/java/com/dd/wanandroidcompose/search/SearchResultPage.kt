package com.dd.wanandroidcompose.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.dd.base.theme.AppTheme
import com.dd.base.theme.ComposeAppTheme
import com.dd.base.theme.Themem
import com.dd.base.widget.SearchBar
import com.dd.base.widget.SwipeRefreshList
import com.dd.wanandroidcompose.R
import com.dd.wanandroidcompose.bean.search.SearchDetails
import com.dd.wanandroidcompose.bean.search.SearchHistory
import com.dd.wanandroidcompose.navigator.RouteName


@Composable
fun SearchResultPage(navCtrl: NavHostController, key: String) {
    val viewModel = hiltViewModel<SearchViewModel>()
    LaunchedEffect(key1 = key) {
        viewModel.dispatch(SearchViewAction.Search)
    }
    var searchKey = viewModel.viewState.searchKey
    var listData = viewModel.viewState.pagingData?.collectAsLazyPagingItems()
    val listState = viewModel.viewState.listState
    ComposeAppTheme(themeType = Themem.themeTypeState.value) {
        Column(modifier = Modifier.fillMaxSize()) {
            //搜索栏
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    ImageBitmap.imageResource(id = R.mipmap.icon_back),
                    contentDescription = "返回",
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            navCtrl.navigateUp()
                        })
                SearchBar(modifier = Modifier.weight(7f), key = searchKey, hint = "jetpack") {
                    searchKey = it
                    viewModel.dispatch(SearchViewAction.SetSearchKey(it))
                }
                Text(
                    text = "搜索",
                    modifier = Modifier
                        .weight(2f)
                        .padding(horizontal = 6.dp)
                        .clickable {
                            //如果没在历史记录中，就添加记录
                            if (SearchHistory(searchKey) !in viewModel.viewState.searchHistory) {
                                viewModel.dispatch(
                                    SearchViewAction.AddSearchHistory(
                                        (SearchHistory(
                                            searchKey
                                        ))
                                    )
                                )
                            }
                            viewModel.dispatch(SearchViewAction.Search)
                        },
                    color = AppTheme.colors.textPrimary,
                    fontSize = 14.sp
                )
            }

            if (listData != null) {
                SwipeRefreshList(
                    modifier = Modifier.fillMaxSize(),
                    lazyPagingItems = listData,
                    listState = listState,
                    refresh = {
                        listData.refresh()
                    },
                    itemContent = {
                        itemsIndexed(listData) { index, item ->
                            SearchItem(
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
    }
}

@Composable
fun SearchItem(data: SearchDetails, onClick: (() -> Unit), collectOnClick: (() -> Unit)) {
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
                    text = data.author,
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