package com.dd.wanandroidcompose.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.navigation.NavHostController
import com.dd.base.theme.AppTheme
import com.dd.base.theme.ComposeAppTheme
import com.dd.base.theme.Themem
import com.dd.base.utils.sdp
import com.dd.wanandroidcompose.R
import com.dd.wanandroidcompose.main.home.HomePage
import com.dd.wanandroidcompose.main.main.MinePage
import com.dd.wanandroidcompose.main.project.ProjectPage
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainPage(navCtrl: NavHostController) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("首页", "项目", "我的")
    ComposeAppTheme(themeType = Themem.themeTypeState.value) {
        Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = {
                //   MainDrawerPage(scaffoldState, scope)
            },
            //抽屉手势关闭，默认开启（开始后滑动屏幕打开抽屉）
            drawerGesturesEnabled = false,
            bottomBar = {
                BottomNavigation(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.sdp),
                    backgroundColor = AppTheme.colors.background
                ) {
                    items.forEachIndexed { index, item ->
                        BottomNavigationItem(
                            icon = {
                                when (index) {
                                    0 -> Icon(Icons.Filled.Home, contentDescription = "首页")
                                    1 -> Icon(
                                        ImageBitmap.imageResource(id = R.mipmap.main_project),
                                        contentDescription = "项目"
                                    )
                                    else -> Icon(Icons.Filled.Person, contentDescription = "我的")
                                }
                            },
                            label = { Text(item) },
                            selectedContentColor = AppTheme.colors.error,
                            unselectedContentColor = AppTheme.colors.textPrimary,
                            selected = selectedItem == index,
                            onClick = { selectedItem = index }
                        )
                    }
                }
            },
        ) {
            // 此处需要编写主界面
            Box(modifier = Modifier.padding(it)) {
                AnimatedContent(targetState = selectedItem) { targetState ->
                    when (targetState) {
                        0 -> HomePage(navCtrl, scaffoldState = scaffoldState, scope = scope)
                        1 -> ProjectPage(navCtrl, scaffoldState = scaffoldState, scope = scope)
                        else -> MinePage(navCtrl, scaffoldState = scaffoldState, scope = scope)
                    }
                }
            }
        }
    }
}