package com.dd.wanandroidcompose.search

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.paging.PagingData
import com.dd.base.base.BaseViewModel
import com.dd.base.ext.launch
import com.dd.base.paging.simplePager
import com.dd.wanandroidcompose.API
import com.dd.wanandroidcompose.bean.home.ListWrapper
import com.dd.wanandroidcompose.bean.project.CategoryDetails
import com.dd.wanandroidcompose.bean.search.HotKey
import com.dd.wanandroidcompose.bean.search.SearchDetails
import com.dd.wanandroidcompose.bean.search.SearchHistory
import com.dd.wanandroidcompose.bean.wechat.WeChatCategoryDetails
import com.dd.wanandroidcompose.net.RxHttpUtils
import com.dd.wanandroidcompose.utils.RoomUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
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
            is SearchViewAction.DelSearchHistory -> delSearchHistory(action.del)
            is SearchViewAction.AddSearchHistory -> addSearchHistory(action.add)
            is SearchViewAction.SetSearchKey -> setSearchKey(action.key)
            is SearchViewAction.Search -> search()
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

    private fun search(){
        viewState = viewState.copy(pagingData = simplePager {
            RxHttpUtils.postAwait<ListWrapper<SearchDetails>>(
                API.Search.search(it),
                mapOf("k" to viewState.searchKey)
            )!!.datas
        })
    }
}

sealed class SearchViewAction {
    object GetSearchHistory : SearchViewAction()
    data class DelSearchHistory(val del: SearchHistory) : SearchViewAction()
    data class AddSearchHistory(val add: SearchHistory) : SearchViewAction()
    data class SetSearchKey(val key: String) : SearchViewAction()
    object Search : SearchViewAction()
}

typealias PagingSearch = Flow<PagingData<SearchDetails>>

data class SearchViewState(
    var searchHistory: List<SearchHistory> = emptyList(),
    var hotKey: List<HotKey> = emptyList(),
    var searchKey: String = "",
    var pagingData: PagingSearch?=null,
    val listState: LazyListState = LazyListState(),
)