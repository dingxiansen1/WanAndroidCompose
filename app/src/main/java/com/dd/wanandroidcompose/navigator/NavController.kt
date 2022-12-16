package com.dd.wanandroidcompose.navigator

import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dd.base.widget.WebViewPage
import com.dd.wanandroidcompose.main.MainPage
import com.dd.wanandroidcompose.search.SearchPage

@Composable
fun NavController() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = RouteName.Main) {
        composable(RouteName.Main) { MainPage(navController) } //主页面
        composable(RouteName.Search) { SearchPage(navController) } //搜索
        //WebView
        composable(
            route = RouteName.Web + "/{webData}",
            arguments = listOf(
                navArgument("url") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType })
        ) {
            val url = it.arguments?.getString("url")
            val title = it.arguments?.getString("title")
            if (url != null) {
                WebViewPage(url, title, navController)
            }
        }
    }


}