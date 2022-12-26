package com.dd.wanandroidcompose.room

import android.content.Context
import androidx.room.*
import com.dd.wanandroidcompose.bean.search.SearchHistory
import com.dd.wanandroidcompose.bean.wechat.WeChatCategory

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearchHistory(category: SearchHistory)

    @Delete
    suspend fun delSearchHistory(category: SearchHistory)

    @Query("SELECT * FROM searchHistory")
    suspend fun getSearchHistory(): List<SearchHistory>?

}

@Database(entities = [SearchHistory::class], version = 1, exportSchema = false)
abstract class SearchHistoryRoomDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        //保证不同线程对这个共享变量进行操作的可见性，并且禁止进行指令重排序.
        @Volatile
        private var INSTANCE: SearchHistoryRoomDatabase? = null

        const val DATA_BASE_NAME = "SearchHistoryRoomDatabase"

        fun getDatabase(context: Context): SearchHistoryRoomDatabase {
            val instance = INSTANCE
            instance?.let { return it }
            synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    SearchHistoryRoomDatabase::class.java,
                    DATA_BASE_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }
}