package com.dd.wanandroidcompose.main.wechat

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
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
import androidx.paging.compose.itemsIndexed
import com.dd.base.theme.AppTheme
import com.dd.wanandroidcompose.R
import com.dd.wanandroidcompose.bean.wechat.WeChatCategoryDetails
import com.dd.wanandroidcompose.navigator.RouteName
import com.dd.wanandroidcompose.widget.pullRefreshLayout
import com.dd.wanandroidcompose.widget.rememberPullRefreshLayoutState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeChatListPage(cid: Int, navCtrl: NavHostController) {
    /* val viewModel = viewModelInstance {
         WeChatListViewModel(cid)
     }*/
    val viewModel: WeChatListViewModel = viewModel(key = cid.toString(), factory = remember {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return WeChatListViewModel(cid) as T
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
        LazyColumn(
            Modifier
                .fillMaxSize()
                .graphicsLayer {
                translationY = state.position
            },
        ) {
            itemsIndexed(listData) { _, item ->
                WeChatItem(item!!, navCtrl)
            }
        }
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
fun WeChatItem(data: WeChatCategoryDetails, navCtrl: NavHostController) {
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
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                data.collect = !data.collect
            }) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "收藏",
                    tint = if (data.collect) AppTheme.colors.error else AppTheme.colors.divider
                )
            }
            Column() {
                Text(
                    text = data.title,
                    style = TextStyle(fontSize = 12.sp, color = AppTheme.colors.textPrimary),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "时间:  ${data.niceDate}",
                    style = TextStyle(fontSize = 10.sp, color = AppTheme.colors.textPrimary),
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