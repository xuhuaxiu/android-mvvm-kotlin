package com.example.petdating.utils

import com.example.petdating.lifecycle.UnPeekLiveData
import com.example.petdating.model.NetState

/**
 *created by xiuer on
 *remark:  网络状态管理单例，通过 [mNetworkStateCallback] 通知观察者网络变化。
 **/
object  NetworkStateManager { // 等同于线程安全的单例
    val networkState = UnPeekLiveData<NetState>()

    // 可选：初始化时注册默认网络状态（如未知状态）
    init {
        networkState.value = NetState(false)
    }
}