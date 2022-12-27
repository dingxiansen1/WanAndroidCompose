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
import com.dd.wanandroidcompose.search.SearchResultPage

@Composable
fun NavController() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = RouteName.Main) {
        composable(RouteName.Main) { MainPage(navController) } //主页面
        composable(RouteName.Search) { SearchPage(navController) } //搜索
        //搜索结果页
        composable(RouteName.SearchResult+ "?key={key}", arguments = listOf(
            navArgument("key") { type = NavType.StringType })) {
            val key = it.arguments?.getString("key") ?:""
            SearchResultPage(navController,key)
        }
        //WebView
        composable(
            route = RouteName.Web + "?link={link}&title={title}",
            arguments = listOf(
                navArgument("link") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType })
        ) {
            val link = it.arguments?.getString("link") ?:"https://www.wanandroid.com"
            val title = it.arguments?.getString("title")
            WebViewPage(link, title, navController)
        }
    }


}