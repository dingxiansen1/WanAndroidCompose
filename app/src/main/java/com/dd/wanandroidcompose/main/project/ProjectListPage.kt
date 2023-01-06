package com.dd.wanandroidcompose.main.project

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.dd.base.theme.AppTheme
import com.dd.wanandroidcompose.R
import com.dd.wanandroidcompose.bean.project.CategoryDetails
import com.dd.wanandroidcompose.navigator.RouteName
import com.dd.wanandroidcompose.widget.pullRefreshLayout
import com.dd.wanandroidcompose.widget.rememberPullRefreshLayoutState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ProjectListPage(
    cid: Int, navCtrl: NavHostController
) {
    /*  val viewModel: ProjectListViewModel = hiltViewModel()
     val listData = viewModel.projectList(cid).collectAsLazyPagingItems()  */
    val viewModel: ProjectListViewModel = viewModel(key = cid.toString(), factory = remember {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ProjectListViewModel(cid) as T
            }
        }
    })
    val listData = viewModel.viewStates.data.collectAsLazyPagingItems()

    val loadingResId = listOf(
        R.drawable.loading_big_1,
        R.drawable.loading_big_4,
        R.drawable.loading_big_7,
        R.drawable.loading_big_10,
        R.drawable.loading_big_13,
        R.drawable.loading_big_16,
        R.drawable.loading_big_19,
    )

    val loadingHeightPx: Float
    with(LocalDensity.current) {
        loadingHeightPx = 16.dp.toPx()
    }

    val loadingAnimate by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = loadingResId.size.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(250, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    var refreshing by remember {
        mutableStateOf(false)
    }
    // 用协程模拟一个耗时加载
    val scope = rememberCoroutineScope()
    val state = rememberPullRefreshLayoutState(refreshing = refreshing, onRefresh = {
        scope.launch {
            refreshing = true
            listData.refresh()
            delay(500)
            refreshing = false
        }
    })
    Box(Modifier.pullRefreshLayout(state)) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationY = state.position
                },
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
                        ProjectListItem(it, navCtrl)
                    }
                }
            })
        // Custom progress indicator
        val id = if (refreshing) loadingAnimate else state.position % loadingResId.size
        if (refreshing || (state.position >= loadingHeightPx * 0.5f)) {
            Image(
                painter = painterResource(loadingResId[id.toInt()]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp, 16.dp)
                    .align(Alignment.TopCenter)
                    .graphicsLayer {
                        translationY = state.position * 0.5f
                    }
            )
        }
    }
}

@Composable
fun ProjectListItem(data: CategoryDetails, navCtrl: NavHostController) {
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