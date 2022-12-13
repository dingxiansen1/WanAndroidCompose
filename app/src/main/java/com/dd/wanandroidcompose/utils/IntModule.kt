package com.dd.wanandroidcompose.utils

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//这里使用了SingletonComponent，因此 IntModule 绑定到 Application 的生命周期
@Module
@InstallIn(SingletonComponent::class)
class IntModule{

    @Singleton
    @Provides
    fun provideIntModule(cid :Int): Int = cid

}