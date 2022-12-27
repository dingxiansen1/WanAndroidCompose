package com.dd.wanandroidcompose.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dd.base.theme.AppTheme
import com.dd.base.theme.ComposeAppTheme
import com.dd.base.theme.Themem
import com.dd.base.utils.sdp
import com.dd.base.widget.SearchBar
import com.dd.wanandroidcompose.bean.search.SearchHistory
import com.dd.wanandroidcompose.navigator.RouteName
import com.google.accompanist.flowlayout.FlowRow


@Composable
fun SearchPage(navCtrl: NavHostController) {
    val viewModel = hiltViewModel<SearchViewModel>()
    var searchKey by remember {
        mutableStateOf("")
    }
    ComposeAppTheme(themeType = Themem.themeTypeState.value) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SearchBar(modifier = Modifier.width(300.dp), hint = "jetpack") {
                    searchKey = it
                }
                Text(
                    text = "搜索",
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .clickable {
                            //如果没在历史记录中，就添加记录
                            if (SearchHistory(searchKey) !in viewModel.viewState.searchHistory) {
                                viewModel.addSearchHistory((SearchHistory(searchKey)))
                            }
                            navCtrl.navigate("${RouteName.SearchResult}?key=${searchKey}")
                        },
                    color = AppTheme.colors.textPrimary,
                    fontSize = 14.sp
                )
            }
            if (viewModel.viewState.hotKey.isNotEmpty()) {
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
                    viewModel.viewState.hotKey.forEach {
                        Box(
                            modifier = Modifier
                                .background(
                                    AppTheme.colors.divider, CircleShape
                                )
                                .clip(CircleShape)
                                .clickable {
                                    viewModel.addSearchHistory(SearchHistory(it.name))
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

        }
    }
}