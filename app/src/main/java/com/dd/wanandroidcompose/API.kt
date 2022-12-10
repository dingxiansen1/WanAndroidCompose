package com.dd.wanandroidcompose

object API {

    private const val BASE_URL="https://www.wanandroid.com"

    object Home{
        const val Banner = "$BASE_URL/banner/json"

        fun homeDate(page:Int):String{
           return "https://www.wanandroid.com/article/list/$page/json"
        }

    }
    object Project{
        const val Category = "$BASE_URL/project/tree/json"
    }
}