package com.example.petdating.ext

import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.petdating.base.activity.BaseVmDbActivity
import com.example.petdating.base.fragment.BaseVmFragment
import com.example.petdating.base.viewmodel.BaseViewModel
import com.example.petdating.network.AppException
import com.example.petdating.network.BaseResponse
import com.example.petdating.network.ResultState
import com.example.petdating.network.paresException
import com.example.petdating.network.paresResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 *created by xiuer on
 *remark:  MVVM + 协程常用的封装方式
 *  * 并行 request 解析数据或返回异常
 *  * @param step async的Deferred<BaseResponse<T>>
 *  * @param resultState 请求回调的ResultState数据
 *  定义了一个 扩展函数，用于在 ViewModel 中统一处理协程异步请求结果，并自动将结果派发给 LiveData
 **/

suspend fun <T> BaseViewModel.requestAsync(step: Deferred<BaseResponse<T>>, resultState: MutableLiveData<ResultState<T>>){
    runCatching { //Kotlin 的异常处理函数
        step.await() //等待 Deferred 执行完成（即网络请求结果）,如果成功，走 onSuccess 分支；如果失败（抛异常），走 onFailure 分支
    }.onSuccess {
        resultState.paresResult(it)
    }.onFailure {
        resultState.paresException(it)
    }
}


/**
 * 同步 request 解析数据并且返回结果
 * @param block 请求体方法
 * @param resultState 请求回调的ResultState数据
 */
suspend fun <T> BaseViewModel.requestSync(
    block: suspend () -> BaseResponse<T>,
    resultState: MutableLiveData<ResultState<T>>
): BaseResponse<T>? {
    runCatching {
        withContext(Dispatchers.IO) {
            block()
        }
    }.onSuccess {
        resultState.paresResult(it)
        return it
    }.onFailure {
        resultState.paresException(it)
        return null
    }
    return null
}


/**
 * 显示页面状态，这里有个技巧，成功回调在第一个，其后两个带默认值的回调可省
 * @param resultState 接口返回值
 * @param onLoading 加载中
 * @param onSuccess 成功回调
 * @param onError 失败回调
 *
 */
fun <T> BaseVmDbActivity<*, *>.parseState(
    resultState: ResultState<T>,
    onSuccess: (T) -> Unit,
    onError: ((AppException) -> Unit)? = null,
    onLoading: (() -> Unit)? = null
) {
    when (resultState) {
        is ResultState.Loading -> {
            showLoading(resultState.loadingMessage)
            onLoading?.run { this }
        }
        is ResultState.Success -> {
            dismissLoading()
            onSuccess(resultState.data)
        }
        is ResultState.Error -> {
            dismissLoading()
            onError?.run { this(resultState.error) }
        }
    }
}
/**
 * 显示页面状态，这里有个技巧，成功回调在第一个，其后两个带默认值的回调可省
 * @param resultState 接口返回值
 * @param onLoading 加载中
 * @param onSuccess 成功回调
 * @param onError 失败回调
 *
 */
fun <T> LifecycleService.parseState(
    resultState: ResultState<T>,
    onSuccess: (T) -> Unit,
    onError: ((AppException) -> Unit)? = null,
    onLoading: (() -> Unit)? = null
) {
    when (resultState) {
        is ResultState.Loading -> {
            onLoading?.invoke()
        }
        is ResultState.Success -> {
            onSuccess(resultState.data)
        }
        is ResultState.Error -> {
            onError?.run { this(resultState.error) }
        }
    }
}


/**
 * 显示页面状态，这里有个技巧，成功回调在第一个，其后两个带默认值的回调可省
 * @param resultState 接口返回值
 * @param onLoading 加载中
 * @param onSuccess 成功回调
 * @param onError 失败回调
 *
 */
fun <T> BaseVmFragment<*>.parseState(
    resultState: ResultState<T>,
    onSuccess: (T) -> Unit,
    onError: ((AppException) -> Unit)? = null,
    onLoading: (() -> Unit)? = null
) {
    when (resultState) {
        is ResultState.Loading -> {
            showLoading(resultState.loadingMessage)
            onLoading?.invoke()
        }
        is ResultState.Success -> {
            dismissLoading()
            onSuccess(resultState.data)
        }
        is ResultState.Error -> {
            dismissLoading()
            onError?.run { this(resultState.error) }
        }
    }
}