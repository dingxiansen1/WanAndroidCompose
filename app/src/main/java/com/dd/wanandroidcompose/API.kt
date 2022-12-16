package com.dd.wanandroidcompose

object API {

    private const val BASE_URL="https://www.wanandroid.com"

    object Home{
        const val Banner = "$BASE_URL/banner/json"

        fun homeDate(page:Int):String{
           return "$BASE_URL/article/list/$page/json"
        }

    }
    object Project{
        const val Category = "$BASE_URL/project/tree/json"

        fun projectList(page: Int):String{
            return "$BASE_URL/project/list/$page/json"
        }
    }
    object WeChat{
        const val Category = "$BASE_URL/wxarticle/chapters/json"

        fun projectList(id:Int,page: Int):String{
            return "$BASE_URL/wxarticle/list/$id/$page/json"
        }
    }
    object Search{
        //post
        fun search(page:Int):String{
            return "$BASE_URL/article/query/$page/json"
        }
    }
}