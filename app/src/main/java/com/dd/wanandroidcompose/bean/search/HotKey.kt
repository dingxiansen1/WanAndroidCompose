package com.dd.wanandroidcompose.bean.search

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Keep
data class HotKey(
    @SerializedName("id") var id: Long = 0,
    @SerializedName("link") var link: String = "",
    @SerializedName("name") var name: String = ""
)
@Keep
@Entity(tableName = "searchHistory")
data class SearchHistory(
    @PrimaryKey
    var name: String
)