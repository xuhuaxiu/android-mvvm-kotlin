package com.xhqb.jetpackmvvm.network.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.example.petdating.utils.NetworkStateManager
import com.example.petdating.utils.NetworkUtil

/**
 *created by xiuer on
 *remark: 网络变化接收器
 */
class NetworkStateReceive : BroadcastReceiver() {
    var isInit = true
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            if(!isInit){
                if (!NetworkUtil.isNetworkAvailable(context)) {
                    //收到没有网络时判断之前的值是不是有网络，如果有网络才提示通知 ，防止重复通知
                    NetworkStateManager.networkState.value?.let {
                        if(it.isSuccess){
                            //没网
                            NetworkStateManager.updateNetworkState( false)
                        }
                        return
                    }
                    NetworkStateManager.updateNetworkState( false)

                }else{
                    //收到有网络时判断之前的值是不是没有网络，如果没有网络才提示通知 ，防止重复通知
                    NetworkStateManager.networkState.value?.let {
                        if(!it.isSuccess){
                            //有网络了
                            NetworkStateManager.updateNetworkState( true)
                        }
                        return
                    }

                    NetworkStateManager.updateNetworkState( true)
                }
            }
            isInit = false
        }
    }
}