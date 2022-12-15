package com.dd.wanandroidcompose.utils

import android.content.Context
import com.dd.wanandroidcompose.bean.project.Category
import com.dd.wanandroidcompose.bean.wechat.WeChatCategory
import com.dd.wanandroidcompose.room.ProjectCategoryRoomDatabase
import com.dd.wanandroidcompose.room.WeChatCategoryRoomDatabase

object RoomUtils {
    lateinit var  projectCategoryRoomDatabase: ProjectCategoryRoomDatabase
    lateinit var  weChatCategoryRoomDatabase: WeChatCategoryRoomDatabase

    fun initRoom(context: Context){
        projectCategoryRoomDatabase = ProjectCategoryRoomDatabase.getDatabase(context)
        weChatCategoryRoomDatabase = WeChatCategoryRoomDatabase.getDatabase(context)
    }

    /**
     *设置项目分类
     **/
    suspend fun setProjectCategory(list: List<Category>) = projectCategoryRoomDatabase.projectCategoryDao().setProjectCategory(list)

    /**
     *获取项目分类
     **/
    suspend fun getProjectCategory():List<Category>? =  projectCategoryRoomDatabase.projectCategoryDao().getProjectCategory()

    /**
     *设置项目分类
     **/
    suspend fun setWeChatCategory(list: List<WeChatCategory>) = weChatCategoryRoomDatabase.weChatCategoryDao().setWeChatCategory(list)

    /**
     *获取项目分类
     **/
    suspend fun getWeChatCategory():List<WeChatCategory>? =  weChatCategoryRoomDatabase.weChatCategoryDao().getWeChatCategory()

}