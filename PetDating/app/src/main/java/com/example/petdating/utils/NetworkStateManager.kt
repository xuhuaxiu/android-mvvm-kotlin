package com.example.petdating.utils

import androidx.lifecycle.LiveData
import com.example.petdating.lifecycle.UnPeekLiveData
import com.example.petdating.model.NetState

/**
 *created by xiuer on
 *remark:  网络状态管理单例，通过 [mNetworkStateCallback] 通知观察者网络变化。
 **/

object NetworkStateManager {
    private val _networkState = UnPeekLiveData<NetState>()
    val networkState: LiveData<NetState> = _networkState

    // 可选：初始化时注册默认网络状态（如未知状态）
    init {
        _networkState.postValue(NetState(false))
    }
    fun updateNetworkState(isAvailable: Boolean) {
        _networkState.postValue(NetState(isAvailable))
    }

}