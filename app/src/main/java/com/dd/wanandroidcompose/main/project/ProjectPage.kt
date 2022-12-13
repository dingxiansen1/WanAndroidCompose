package com.dd.wanandroidcompose.main.project

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dd.base.theme.AppTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProjectPage(navCtrl: NavHostController, scaffoldState: ScaffoldState) {
    val viewModel: ProjectViewModel = hiltViewModel()
    val titles = viewModel.viewStates.category
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    Column(Modifier.fillMaxSize()) {
        ScrollableTabRow(
            // Our selected tab is our current page
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = AppTheme.colors.background,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }) {
            // Add tabs for all of our pages
            titles.forEachIndexed { index, title ->
                Tab(text = { Text(text = title.name, color = AppTheme.colors.textPrimary) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        // 迁移页面
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    })
            }
        }

        HorizontalPager(
            count = titles.size, state = pagerState,
            // Add 16.dp padding to 'center' the pages
            contentPadding = PaddingValues(16.dp), modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            ProjectListPage(titles[page].id)
        }
    }
}
