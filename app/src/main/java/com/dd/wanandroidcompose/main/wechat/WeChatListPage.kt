package com.dd.wanandroidcompose.main.wechat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                itemsIndexed(listData) { _, item ->
                    WeChatItem(item!!)
                }

            })

    }
}
@Composable
fun WeChatItem(data: WeChatCategoryDetails){
   Text(text = data.title)
}