package com.dd.wanandroidcompose.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dd.base.theme.AppTheme
import com.dd.base.theme.ComposeAppTheme
import com.dd.base.theme.Themem
import com.dd.base.widget.SearchBar
import com.dd.wanandroidcompose.bean.search.SearchHistory
import com.dd.wanandroidcompose.navigator.RouteName
import com.google.accompanist.flowlayout.FlowRow


@Composable
fun SearchPage(navCtrl: NavHostController) {
    val viewModel = hiltViewModel<SearchViewModel>()
    LaunchedEffect(key1 = null){
        viewModel.dispatch(SearchViewAction.GetSearchHistory)
    }
    val hotkeys = viewModel.viewState.hotKey
    val searchHistory = viewModel.viewState.searchHistory
    var searchKey =viewModel.viewState.searchKey
    ComposeAppTheme(themeType = Themem.themeTypeState.value) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SearchBar(modifier = Modifier.weight(5f), key = searchKey, hint = "jetpack") {
                    searchKey = it
                    viewModel.dispatch(SearchViewAction.SetSearchKey(it))
                }
                Text(
                    text = "搜索",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 6.dp)
                        .clickable {
                            //如果没在历史记录中，就添加记录
                            if (SearchHistory(searchKey) !in viewModel.viewState.searchHistory) {
                                viewModel.dispatch(SearchViewAction.AddSearchHistory((SearchHistory(searchKey))))
                            }
                            navCtrl.navigate("${RouteName.SearchResult}?key=${searchKey}")
                        },
                    color = AppTheme.colors.textPrimary,
                    fontSize = 14.sp
                )
            }
            if (hotkeys.isNotEmpty()) {
                Text(
                    text = "热门搜索",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = AppTheme.colors.textPrimary,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 8.dp,
                ) {
                    hotkeys.forEach {
                        Box(
                            modifier = Modifier
                                .background(
                                    AppTheme.colors.divider, CircleShape
                                )
                                .clip(CircleShape)
                                .clickable {
                                    viewModel.dispatch(SearchViewAction.AddSearchHistory((SearchHistory(it.name))))
                                    navCtrl.navigate("${RouteName.SearchResult}?key=${it.name}")
                                }
                        ) {
                            Text(
                                text = it.name,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                color = AppTheme.colors.background,
                                fontSize = 14.sp,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
            if (searchHistory.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "搜索历史",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = AppTheme.colors.textPrimary,
                    fontSize = 16.sp
                )
                LazyColumn(content = {
                    itemsIndexed(searchHistory) { index, item ->
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navCtrl.navigate("${RouteName.SearchResult}?key=${searchKey}")
                            }) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp)
                            ) {
                                Text(
                                    text = item.name,
                                    modifier = Modifier
                                        .weight(1f)
                                        .align(Alignment.CenterVertically),
                                    color = AppTheme.colors.textPrimary,
                                    fontSize = 14.sp,
                                    maxLines = 1
                                )
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "删除",
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable {
                                            viewModel.dispatch(SearchViewAction.RemoveSearchHistory(item))
                                        },
                                    tint = MaterialTheme.colors.onBackground
                                )
                            }
                        }
                    }
                })
            }
        }
    }
}