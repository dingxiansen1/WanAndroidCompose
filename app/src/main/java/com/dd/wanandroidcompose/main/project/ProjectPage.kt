package com.dd.wanandroidcompose.main.project

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope


@Composable
fun ProjectPage(navCtrl: NavHostController, scaffoldState: ScaffoldState, scope: CoroutineScope) {
    val viewModel: ProjectViewModel = hiltViewModel()


}
