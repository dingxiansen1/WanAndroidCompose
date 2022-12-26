package com.dd.wanandroidcompose.utils

import android.content.Context
import com.dd.wanandroidcompose.bean.project.Category
import com.dd.wanandroidcompose.bean.search.SearchHistory
import com.dd.wanandroidcompose.bean.wechat.WeChatCategory
import com.dd.wanandroidcompose.room.ProjectCategoryRoomDatabase
import com.dd.wanandroidcompose.room.SearchHistoryRoomDatabase
import com.dd.wanandroidcompose.room.WeChatCategoryRoomDatabase

object RoomUtils {
    lateinit var  projectCategoryRoomDatabase: ProjectCategoryRoomDatabase
    lateinit var  weChatCategoryRoomDatabase: WeChatCategoryRoomDatabase
    lateinit var searchHistoryRoomDatabase: SearchHistoryRoomDatabase

    fun initRoom(context: Context){
        projectCategoryRoomDatabase = ProjectCategoryRoomDatabase.getDatabase(context)
        weChatCategoryRoomDatabase = WeChatCategoryRoomDatabase.getDatabase(context)
        searchHistoryRoomDatabase = SearchHistoryRoomDatabase.getDatabase(context)
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

    /**
     *增加搜索历史
     **/
    suspend fun addSearchHistory(item: SearchHistory) = searchHistoryRoomDatabase.searchHistoryDao().addSearchHistory(item)
    /**
     *删除搜索历史
     **/
    suspend fun delSearchHistory(item: SearchHistory) = searchHistoryRoomDatabase.searchHistoryDao().delSearchHistory(item)
    /**
     *获取搜索历史
     **/
    suspend fun getSearchHistory():List<SearchHistory>? =  searchHistoryRoomDatabase.searchHistoryDao().getSearchHistory()

}