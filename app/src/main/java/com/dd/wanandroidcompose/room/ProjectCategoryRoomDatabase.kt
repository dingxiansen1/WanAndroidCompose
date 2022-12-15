package com.dd.wanandroidcompose.room

import android.content.Context
import androidx.room.*
import com.dd.wanandroidcompose.bean.project.Category

@Dao
interface ProjectCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setProjectCategory( category: List<Category>)


    @Query("SELECT * FROM projectCategory")
    suspend fun getProjectCategory(): List<Category>?

}

@Database(entities = [Category::class], version = 1 , exportSchema = false)
abstract class ProjectCategoryRoomDatabase :RoomDatabase(){
    abstract fun projectCategoryDao(): ProjectCategoryDao

    companion object {
        //保证不同线程对这个共享变量进行操作的可见性，并且禁止进行指令重排序.
        @Volatile
        private var INSTANCE: ProjectCategoryRoomDatabase? = null

        const val DATA_BASE_NAME = "ProjectCategoryRoomDatabase"

        fun getDatabase(context: Context): ProjectCategoryRoomDatabase {
            val instance = INSTANCE
            instance?.let { return it }
            synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    ProjectCategoryRoomDatabase::class.java,
                    DATA_BASE_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }
}