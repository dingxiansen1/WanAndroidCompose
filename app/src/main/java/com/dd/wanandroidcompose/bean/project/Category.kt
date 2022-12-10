package com.dd.wanandroidcompose.bean.project

import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


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


@Serializable
data class CategoryDetails(
    @SerialName("adminAdd")
    var adminAdd: Boolean,
    @SerialName("apkLink")
    var apkLink: String,
    @SerialName("audit")
    var audit: Int,
    @SerialName("author")
    var author: String,
    @SerialName("canEdit")
    var canEdit: Boolean,
    @SerialName("chapterId")
    var chapterId: Int,
    @SerialName("chapterName")
    var chapterName: String,
    @SerialName("collect")
    var collect: Boolean,
    @SerialName("courseId")
    var courseId: Int,
    @SerialName("desc")
    var desc: String,
    @SerialName("descMd")
    var descMd: String,
    @SerialName("envelopePic")
    var envelopePic: String,
    @SerialName("fresh")
    var fresh: Boolean,
    @SerialName("host")
    var host: String,
    @SerialName("id")
    var id: Int,
    @SerialName("isAdminAdd")
    var isAdminAdd: Boolean,
    @SerialName("link")
    var link: String,
    @SerialName("niceDate")
    var niceDate: String,
    @SerialName("niceShareDate")
    var niceShareDate: String,
    @SerialName("origin")
    var origin: String,
    @SerialName("prefix")
    var prefix: String,
    @SerialName("projectLink")
    var projectLink: String,
    @SerialName("publishTime")
    var publishTime: Long,
    @SerialName("realSuperChapterId")
    var realSuperChapterId: Int,
    @SerialName("selfVisible")
    var selfVisible: Int,
    @SerialName("shareDate")
    var shareDate: Long,
    @SerialName("shareUser")
    var shareUser: String,
    @SerialName("superChapterId")
    var superChapterId: Int,
    @SerialName("superChapterName")
    var superChapterName: String,
    @SerialName("tags")
    var tags: List<Tag>,
    @SerialName("title")
    var title: String,
    @SerialName("type")
    var type: Int,
    @SerialName("userId")
    var userId: Int,
    @SerialName("visible")
    var visible: Int,
    @SerialName("zan")
    var zan: Int
) {
    @Serializable
    data class Tag(
        @SerialName("name")
        var name: String,
        @SerialName("url")
        var url: String
    )
}
}
