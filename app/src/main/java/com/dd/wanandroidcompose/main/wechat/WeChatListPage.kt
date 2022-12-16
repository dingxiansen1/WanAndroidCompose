package com.dd.wanandroidcompose.main.wechat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.dd.base.theme.AppTheme
import com.dd.base.widget.SwipeRefreshList
import com.dd.wanandroidcompose.bean.wechat.WeChatCategoryDetails
import com.dd.wanandroidcompose.utils.viewModelInstance

@Composable
fun WeChatListPage(cid: Int) {
    val viewModel = viewModelInstance {
        WeChatListViewModel(cid)
    }
    val listData = viewModel.viewStates.data.collectAsLazyPagingItems()

    Column(
        Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
    ) {
        LazyColumn(
            Modifier.fillMaxSize(),
        ) {
            itemsIndexed(listData) { _, item ->
                WeChatItem(item!!)
            }
        }
    }
}

@Composable
fun WeChatItem(data: WeChatCategoryDetails) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
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