package com.dd.wanandroidcompose.main.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.paging.PagingData
import com.dd.base.base.BaseViewModel
import com.dd.base.ext.launch
import com.dd.wanandroidcompose.net.RxHttpUtils
import com.dd.wanandroidcompose.API
import com.dd.wanandroidcompose.bean.home.HomeBanner
import com.dd.wanandroidcompose.bean.home.HomeData
import com.dd.base.paging.ListWrapper
import com.dd.base.paging.simplePager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
) : BaseViewModel() {
    private val pager by lazy {
        simplePager {
            RxHttpUtils.getCAwait<ListWrapper<HomeData>>(API.Home.homeDate(it))
        }
    }
    var viewStates by mutableStateOf(HomeViewState(data = pager))
        private set

    init {
        getBanner()
    }

    fun getBanner() {
        launch {
            val banner = RxHttpUtils.getAwait<List<HomeBanner>>(API.Home.Banner) ?: emptyList()
            if (banner.isNotEmpty()) {
                //缓存在本地
            }

            viewStates = HomeViewState(banner, data = pager)
        }
    }

}

typealias PagingArticle = Flow<PagingData<HomeData>>

data class HomeViewState(
    val banner: List<HomeBanner> = emptyList(),
    val listState: LazyListState = LazyListState(),
    val data: PagingArticle,
)



