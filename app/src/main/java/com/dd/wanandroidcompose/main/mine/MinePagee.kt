package com.dd.wanandroidcompose.main.mine

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.navigation.NavHostController
import com.dd.base.theme.AppTheme
import com.dd.base.theme.ComposeAppTheme
import com.dd.base.theme.Themem
import com.dd.wanandroidcompose.R

@Composable
fun MinePage(navCtrl: NavHostController) {
    ComposeAppTheme(themeType = Themem.themeTypeState.value) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(horizontalAlignment = Alignment.End) {
                Row(Modifier.fillMaxWidth()) {
                    Icon(
                        ImageBitmap.imageResource(id = R.mipmap.mine_theme),
                        contentDescription = "主题",
                        tint = AppTheme.colors.textPrimary
                    )
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "设置",
                        tint = AppTheme.colors.textPrimary,
                    )

                }
            }

        }
    }
}
