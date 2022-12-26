package com.dd.wanandroidcompose.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.google.accompanist.flowlayout.FlowRow


@Composable
fun SearchPage(navCtrl: NavHostController) {
    val viewModel = hiltViewModel<SearchViewModel>()
    ComposeAppTheme(themeType = Themem.themeTypeState.value) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(hint = "jetpack") {
                viewModel.viewState.searchKey = it
            }
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
                                color = Color(0xFFDDDDDD),
                                shape = RoundedCornerShape(percent = 50)
                            )
                            .clip(RoundedCornerShape(percent = 50))
                            .clickable {
                                viewModel.addSearchHistory(SearchHistory(it.name))
                                //跳转页面 todo
                            }
                    ) {
                        Text(
                            text = it.name,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            color = AppTheme.colors.textPrimary,
                            fontSize = 14.sp,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}