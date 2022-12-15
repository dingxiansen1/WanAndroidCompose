package com.dd.wanandroidcompose.room

import android.content.Context
import androidx.room.*
import com.dd.wanandroidcompose.bean.wechat.WeChatCategory

@Dao
interface WeChatCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setWeChatCategory(category: List<WeChatCategory>)


    @Query("SELECT * FROM weChatCategory")
    suspend fun getWeChatCategory(): List<WeChatCategory>?

}

@Database(entities = [WeChatCategory::class], version = 1, exportSchema = false)
abstract class WeChatCategoryRoomDatabase : RoomDatabase() {
    abstract fun weChatCategoryDao(): WeChatCategoryDao

    companion object {
        //保证不同线程对这个共享变量进行操作的可见性，并且禁止进行指令重排序.
        @Volatile
        private var INSTANCE: WeChatCategoryRoomDatabase? = null

        const val DATA_BASE_NAME = "WeChatCategoryRoomDatabase"

        fun getDatabase(context: Context): WeChatCategoryRoomDatabase {
            val instance = INSTANCE
            instance?.let { return it }
            synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    WeChatCategoryRoomDatabase::class.java,
                    DATA_BASE_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }
}