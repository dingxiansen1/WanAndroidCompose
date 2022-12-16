package com.dd.wanandroidcompose.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.dd.base.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : BaseViewModel() {

    var viewState by mutableStateOf(SearchViewState())

}

data class SearchViewState(
    var searchKey:String = "",
    var searchHistory: List<String> = emptyList(),
)