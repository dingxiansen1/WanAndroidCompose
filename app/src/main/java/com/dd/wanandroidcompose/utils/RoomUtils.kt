package com.dd.wanandroidcompose.utils

import android.content.Context
import com.dd.wanandroidcompose.bean.project.Category
import com.dd.wanandroidcompose.room.ProjectCategoryRoomDatabase

object RoomUtils {
    lateinit var  projectCategoryRoomDatabase: ProjectCategoryRoomDatabase

    fun initRoom(context: Context){
        projectCategoryRoomDatabase = ProjectCategoryRoomDatabase.getDatabase(context)
    }

    /**
     *设置项目分类
     **/
    suspend fun setProjectCategory(list: List<Category>) = projectCategoryRoomDatabase.projectCategoryDao().setProjectCategory(list)

    /**
     *获取项目分类
     **/
    suspend fun getProjectCategory():List<Category>? =  projectCategoryRoomDatabase.projectCategoryDao().getProjectCategory()

}