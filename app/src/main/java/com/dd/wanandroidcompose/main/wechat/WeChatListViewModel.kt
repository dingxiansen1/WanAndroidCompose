package com.dd.wanandroidcompose.main.wechat

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.paging.PagingData
import com.dd.base.base.BaseViewModel
import com.dd.base.paging.simplePager
import com.dd.base.utils.log.LogUtils
import com.dd.wanandroidcompose.API
import com.dd.wanandroidcompose.bean.home.HomeBanner
import com.dd.wanandroidcompose.bean.home.ListWrapper
import com.dd.wanandroidcompose.bean.wechat.WeChatCategoryDetails
import com.dd.wanandroidcompose.net.RxHttpUtils
import kotlinx.coroutines.flow.Flow

class WeChatListViewModel constructor(cid :Int) :
    BaseViewModel() {

    val pager by lazy {
        simplePager {
            RxHttpUtils.getAwait<ListWrapper<WeChatCategoryDetails>>(
                API.WeChat.projectList(cid, it)
            )!!.datas
        }
    }
    var viewStates by mutableStateOf(WeChatListViewState(data = pager))
        private set

    override fun onCleared() {
        super.onCleared()
        LogUtils.d("丁笛WeChatListViewModel，销毁")
    }

}


typealias PagingWeChat = Flow<PagingData<WeChatCategoryDetails>>


data class WeChatListViewState(
    val banner: List<HomeBanner> = emptyList(),
    val listState: LazyListState = LazyListState(),
    val data: PagingWeChat,
)