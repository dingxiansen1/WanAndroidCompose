package com.dd.wanandroidcompose.main.mine

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dd.base.theme.AppTheme
import com.dd.base.theme.ComposeAppTheme
import com.dd.base.theme.Themem
import com.dd.wanandroidcompose.R

@Composable
fun MinePage(navCtrl: NavHostController) {
    ComposeAppTheme(themeType = Themem.themeTypeState.value) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                val size = Modifier.size(20.dp)
                Icon(
                    ImageBitmap.imageResource(id = R.mipmap.mine_theme),
                    contentDescription = "主题",
                    tint = AppTheme.colors.textPrimary,
                    modifier = size.padding(end = 30.dp)
                )
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "设置",
                    tint = AppTheme.colors.textPrimary,
                    modifier = size
                )

            }
        }
    }
}
