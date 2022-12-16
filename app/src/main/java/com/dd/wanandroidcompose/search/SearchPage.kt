package com.dd.wanandroidcompose.search

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dd.base.widget.SearchBar


@Composable
fun SearchPage(navCtrl: NavHostController) {
    val viewModel = hiltViewModel<SearchViewModel>()
    SearchBar(hint = "jetpack"){
        viewModel.viewState.searchKey = it
    }
}