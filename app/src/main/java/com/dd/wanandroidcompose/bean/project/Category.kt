package com.dd.wanandroidcompose.bean.project

import androidx.annotation.Keep
import androidx.room.*
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName
@Keep
@Entity(tableName = "projectCategory")
@Serializable
data class Category(
    @SerialName("author")
    var author: String,
    @SerialName("courseId")
    var courseId: Int,
    @SerialName("cover")
    var cover: String,
    @SerialName("desc")
    var desc: String,
    @SerialName("id")
    @PrimaryKey
    var id: Int,
    @SerialName("lisense")
    var lisense: String,
    @SerialName("lisenseLink")
    var lisenseLink: String,
    @SerialName("name")
    var name: String,
    @SerialName("order")
    var order: Int,
    @SerialName("parentChapterId")
    var parentChapterId: Int,
    @SerialName("type")
    var type: Int,
    @SerialName("userControlSetTop")
    var userControlSetTop: Boolean,
    @SerialName("visible")
    var visible: Int
)
