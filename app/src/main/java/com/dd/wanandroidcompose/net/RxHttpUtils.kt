package com.dd.wanandroidcompose.net

import com.dd.base.paging.BaseResponse
import com.dd.base.utils.log.LogUtils
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import rxhttp.asFlow
import rxhttp.toClass
import rxhttp.wrapper.param.RxHttp

object RxHttpUtils {

    var CodeSuccess :Int= 0

    suspend inline fun <reified T> getCAwait(url: String, parameter: Map<String, Any> = mapOf()): BaseResponse<T> {
        return try {
            RxHttp.get(url).addAll(parameter).toClass<BaseResponse<T>>().await()
        }catch (e:Exception){
            BaseResponse()
        }
    }

    suspend inline fun <reified T> getAwait(url: String, parameter: Map<String, Any> = mapOf()): T? {
        return try {
            val data = RxHttp.get(url).addAll(parameter).toClass<BaseResponse<T>>().await()
            if (data.code == CodeSuccess && data.data != null) {
                data.data
            } else {
                LogUtils.d(data.msg)
                null
            }
        }catch (e:Exception){
            null
        }
    }

    suspend inline fun <reified T> postAwait(url: String, parameter: Map<String, Any> = mapOf()): T? {
        return try {
            val data = RxHttp.postJson(url).addAll(parameter).toClass<BaseResponse<T>>().await()
            if (data.code == CodeSuccess && data.data != null) {
                data.data
            } else {
                LogUtils.d(data.msg)
                null
            }
        }catch (e:Exception){
            null
        }
    }

    suspend fun <T> getFlow(
        url: String,
        parameter: Map<String, Any> = mapOf(),
        onSuccess: (T?) -> Unit
    ) {
        RxHttp.get(url).addAll(parameter).toClass<BaseResponse<T>>().asFlow().map {
            if (it.code == CodeSuccess && it.data != null) {
                it.data
            } else {
                LogUtils.d(it.msg)
                null
            }
        }.catch {
            onSuccess.invoke(null)
        }.collect {
            onSuccess.invoke(it)
        }
    }

    suspend fun <T> postFlow(
        url: String,
        parameter: Map<String, Any> = mapOf(),
        onSuccess: (T?) -> Unit
    ) {
        RxHttp.postJson(url).addAll(parameter).toClass<BaseResponse<T>>().asFlow().map {
            if (it.code == CodeSuccess && it.data != null) {
                it.data
            } else {
                LogUtils.d(it.msg)
                null
            }
        }.catch {
            onSuccess.invoke(null)
        }.collect {
            onSuccess.invoke(it)
        }
    }
}