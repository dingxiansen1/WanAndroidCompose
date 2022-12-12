package com.dd.wanandroidcompose

import com.dd.base.base.BaseApp
import com.dd.wanandroidcompose.net.RxHttpUtils
import com.dd.wanandroidcompose.utils.RoomUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class APP :BaseApp() {
    override fun onCreate() {
        super.onCreate()
        RoomUtils.initRoom(this)
    }
}