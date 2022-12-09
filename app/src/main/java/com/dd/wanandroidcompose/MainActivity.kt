package com.dd.wanandroidcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.dd.base.theme.ComposeAppTheme
import com.dd.base.theme.Themem
import com.dd.base.utils.WindowUtils
import com.dd.wanandroidcompose.navigator.NavController
import com.dd.wanandroidcompose.splash.SplashPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化window工具类
        WindowUtils.Init(this)
        setContent {
            ComposeAppTheme(themeType = Themem.themeTypeState.value)  {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //是否闪屏页
                    var isSplash by remember { mutableStateOf(true) }
                    if (isSplash) {
                        WindowUtils.hideSystemUI()
                        SplashPage{ isSplash = false }
                    } else {
                        WindowUtils.showSystemUI()
                        NavController()
                    }
                }
            }
        }
    }
}