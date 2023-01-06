package com.dd.wanandroidcompose.main.wechat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.dd.base.base.BaseViewModel
import com.dd.base.ext.launch
import com.dd.wanandroidcompose.API
import com.dd.wanandroidcompose.bean.wechat.WeChatCategory
import com.dd.wanandroidcompose.net.RxHttpUtils
import com.dd.wanandroidcompose.utils.RoomUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeChatViewModel @Inject constructor() : BaseViewModel() {

    var viewStates by mutableStateOf(WeChatViewState())
        private set

    init {
        getCategory()
    }

    private fun getCategory() {
        launch {
            val cacheList = RoomUtils.getWeChatCategory()
            if (cacheList!=null&& cacheList.isNotEmpty()){
                viewStates = WeChatViewState(cacheList)
                return@launch
            }
            val list = RxHttpUtils.getAwait<List<WeChatCategory>>(API.WeChat.Category) ?: emptyList()
            if (list.isNotEmpty()) {
                viewStates = WeChatViewState(list)
                //缓存在本地
                RoomUtils.setWeChatCategory(list)
            }
        }
    }
}

data class WeChatViewState(
    val category: List<WeChatCategory> = emptyList(),
)