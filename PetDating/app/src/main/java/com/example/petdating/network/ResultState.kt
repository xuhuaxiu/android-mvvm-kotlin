package com.example.petdating.network
import androidx.lifecycle.MutableLiveData

/**
 *created by xiuer on
 *remark: 自定义结果集封装类
 **/

sealed class ResultState<out T> {
    companion object {
        fun <T> onAppSuccess(data: T): ResultState<T> = Success(data)
        fun <T> onAppLoading(loadingMessage:String): ResultState<T> = Loading(loadingMessage)
        fun <T> onAppError(error: AppException): ResultState<T> = Error(error)
    }
    data class Loading(val loadingMessage:String) : ResultState<Nothing>()
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val error: AppException) : ResultState<Nothing>()
}


/**
 * 处理返回值
 * @param result 请求结果
 */
fun <T> MutableLiveData<ResultState<T>>.paresResult(result: BaseResponse<T>) {
    if(result.getResponseData()!= null){
        value = if (result.isSuccess()) ResultState.onAppSuccess(result.getResponseData()) else
            ResultState.onAppError(AppException(result.getResponseCode(), result.getResponseMsg()))
    }else{
        value = ResultState.onAppError(AppException(result.getResponseCode(), result.getResponseMsg()))
    }
}

/**
 * 不处理返回值 直接返回请求结果
 * @param result 请求结果
 */
fun <T> MutableLiveData<ResultState<T>>.paresResult(result: T) {
    value = if(result == null){
        ResultState.onAppError(AppException(500, "请求失败"))
    }else{
        ResultState.onAppSuccess(result)
    }
}

/**
 * 异常转换异常处理
 */
fun <T> MutableLiveData<ResultState<T>>.paresException(e: Throwable) {
    this.value = ResultState.onAppError(ExceptionHandle.handleException(e))
}

