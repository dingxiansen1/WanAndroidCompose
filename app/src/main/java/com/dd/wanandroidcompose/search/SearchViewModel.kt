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
        getHotKey()
    }

    fun dispatch(action: SearchViewAction) {
        when (action) {
            is SearchViewAction.GetSearchHistory -> getSearchHistory()
            is SearchViewAction.RemoveSearchHistory -> delSearchHistory(action.del)
            is SearchViewAction.AddSearchHistory -> addSearchHistory(action.add)
            is SearchViewAction.SetSearchKey -> setSearchKey(action.key)
        }
    }

    private fun setSearchKey(key: String) {
        viewState = viewState.copy(searchKey = key)
    }

    private fun getSearchHistory() {
        launch {
            val searchHistory = RoomUtils.getSearchHistory() ?: emptyList()
            viewState = viewState.copy(searchHistory = searchHistory)
        }

    }

    private fun getHotKey() {
        launch {
            val hotkey = RxHttpUtils.getAwait<List<HotKey>>(API.Search.HotKeys) ?: emptyList()
            viewState = SearchViewState(hotKey = hotkey)
        }
    }

    private fun addSearchHistory(item: SearchHistory) {
        launch {
            RoomUtils.addSearchHistory(item)
        }
    }

    private fun delSearchHistory(item: SearchHistory) {
        launch {
            val searchHistory = viewState.searchHistory as MutableList<SearchHistory>
            searchHistory.remove(item)
            viewState = viewState.copy(searchHistory = searchHistory)
            RoomUtils.delSearchHistory(item)
        }
    }
}

sealed class SearchViewAction {
    object GetSearchHistory : SearchViewAction()
    data class RemoveSearchHistory(val del: SearchHistory) : SearchViewAction()
    data class AddSearchHistory(val add: SearchHistory) : SearchViewAction()
    data class SetSearchKey(val key: String) : SearchViewAction()
}

data class SearchViewState(
    var searchHistory: List<SearchHistory> = emptyList(),
    var hotKey: List<HotKey> = emptyList(),
    var searchKey: String = "",
)