package com.dd.wanandroidcompose.main.wechat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.dd.base.theme.AppTheme
import com.dd.base.widget.SwipeRefreshList
import com.dd.wanandroidcompose.bean.wechat.WeChatCategoryDetails
import com.dd.wanandroidcompose.navigator.RouteName
import com.dd.wanandroidcompose.utils.viewModelInstance

@Composable
fun WeChatListPage(cid: Int,navCtrl: NavHostController) {
   /* val viewModel = viewModelInstance {
        WeChatListViewModel(cid)
    }*/
    val viewModel: WeChatListViewModel = viewModel(key = cid.toString(),factory = remember {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return WeChatListViewModel(cid) as T
            }
        }
    })
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
                WeChatItem(item!!,navCtrl)
            }
        }
    }
}

@Composable
fun WeChatItem(data: WeChatCategoryDetails,navCtrl: NavHostController) {
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