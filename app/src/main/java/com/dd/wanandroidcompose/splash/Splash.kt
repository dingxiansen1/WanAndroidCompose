package com.dd.wanandroidcompose.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dd.base.theme.AppTheme
import com.dd.base.theme.ComposeAppTheme
import com.dd.base.theme.Themem
import com.dd.wanandroidcompose.R
import kotlinx.coroutines.delay

@Composable
fun SplashPage(onNextPage: () -> Unit) {
    ComposeAppTheme(themeType = Themem.themeTypeState.value) {
        var time by remember { mutableStateOf(3) }
        var isShowAD by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            delay(2000)
            isShowAD = true
            for (i in 3 downTo 0) {
                delay(1000)
                time--
            }
            if (time <= 0) {
                onNextPage.invoke()
            }
        }
        Image(
            painter = painterResource(id = R.mipmap.splash),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            contentDescription = "引导"
        )
        AnimatedVisibility(isShowAD) {
            Box {
                AsyncImage(
                    model = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F35a2991dd120fafe7a553e86a4a5a021813bbfea3e4cb-4F1icm_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1655963689&t=af4827c00636bc94f436f901520df92c",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "广告"
                )
                ExtendedFloatingActionButton(modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.TopEnd),
                    backgroundColor = AppTheme.colors.divider,
                    onClick = {
                        onNextPage.invoke()
                    },
                    text = { Text("跳过  $time", color = AppTheme.colors.white) })
            }
        }
    }
}