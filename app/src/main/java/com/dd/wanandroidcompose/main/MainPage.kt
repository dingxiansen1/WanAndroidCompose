package com.dd.wanandroidcompose.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dd.base.theme.AppTheme
import com.dd.base.theme.ComposeAppTheme
import com.dd.base.theme.Themem
import com.dd.base.utils.sdp
import com.dd.wanandroidcompose.R
import com.dd.wanandroidcompose.main.home.HomePage
import com.dd.wanandroidcompose.main.mine.MinePage
import com.dd.wanandroidcompose.main.project.ProjectPage
import com.dd.wanandroidcompose.main.wechat.WeChatPage

@Composable
fun MainPage(navCtrl: NavHostController) {
    val scaffoldState = rememberScaffoldState()
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("首页", "项目", "微信公众号", "我的")
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
                        val size = Modifier.size(20.dp)
                        BottomNavigationItem(
                            icon = {
                                when (index) {
                                    0 -> Icon(
                                        Icons.Filled.Home,
                                        contentDescription = "首页",
                                        modifier = size
                                    )
                                    1 -> Icon(
                                        ImageBitmap.imageResource(id = R.mipmap.main_project),
                                        contentDescription = "项目", modifier = size
                                    )
                                    2 -> Icon(
                                        ImageBitmap.imageResource(id = R.mipmap.main_wechat),
                                        contentDescription = "微信公众号", modifier = size
                                    )
                                    else -> Icon(
                                        Icons.Filled.Person,
                                        contentDescription = "我的",
                                        modifier = size
                                    )
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
                /*AnimatedContent(targetState = selectedItem) { targetState ->
                    when (targetState) {
                        0 -> HomePage(navCtrl)
                        1 -> ProjectPage(navCtrl)
                        2 -> WeChatPage(navCtrl)
                        else -> MinePage(navCtrl)
                    }
                }*/
                when (selectedItem) {
                    0 -> HomePage(navCtrl)
                    1 -> ProjectPage(navCtrl)
                    2 -> WeChatPage(navCtrl)
                    else -> MinePage(navCtrl)
                }
            }
        }
    }
}