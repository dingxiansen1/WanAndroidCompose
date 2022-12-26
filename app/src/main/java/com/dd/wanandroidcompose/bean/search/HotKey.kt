package com.dd.wanandroidcompose.bean.search

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class HotKey(
    @SerializedName("id") var id: Long = 0,
    @SerializedName("link") var link: String = "",
    @SerializedName("name") var name: String = ""
)

@Entity(tableName = "searchHistory")
data class SearchHistory(
    @PrimaryKey
    var name: String
)