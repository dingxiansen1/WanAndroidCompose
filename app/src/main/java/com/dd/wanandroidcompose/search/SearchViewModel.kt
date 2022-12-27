package com.dd.wanandroidcompose.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.dd.base.base.BaseViewModel
import com.dd.base.ext.launch
import com.dd.wanandroidcompose.API
import com.dd.wanandroidcompose.bean.search.HotKey
import com.dd.wanandroidcompose.bean.search.SearchHistory
import com.dd.wanandroidcompose.net.RxHttpUtils
import com.dd.wanandroidcompose.utils.RoomUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : BaseViewModel() {

    var viewState by mutableStateOf(SearchViewState())

    init {
        getData()
    }

    private fun getData() {
        launch {
            val hotkey = async {
               RxHttpUtils.getAwait<List<HotKey>>(API.Search.HotKeys) ?: emptyList()
            }
            val searchHistory = async {
                RoomUtils.getSearchHistory() ?: emptyList()
            }
            viewState = SearchViewState(hotKey = hotkey.await(), searchHistory = searchHistory.await())
        }
    }

    fun addSearchHistory(item :SearchHistory){
        launch {
            RoomUtils.addSearchHistory(item)
        }
    }
    fun delSearchHistory(item :SearchHistory){
        launch {
            RoomUtils.delSearchHistory(item)
        }
    }
}

data class SearchViewState(
    var searchHistory: List<SearchHistory> = emptyList(),
    var hotKey: List<HotKey> = emptyList(),
)