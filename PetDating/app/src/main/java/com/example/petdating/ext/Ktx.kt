package com.xhqb.jetpackmvvm.base

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.content.IntentFilter
import android.database.Cursor
import android.net.ConnectivityManager
import android.net.Uri
import androidx.lifecycle.ProcessLifecycleOwner
import com.xhqb.jetpackmvvm.network.manager.NetworkStateReceive
import com.xhqb.tracklibrary.lifecycle.KtxAppLifeObserver
import com.xhqb.tracklibrary.lifecycle.KtxLifeCycleCallBack

/**
 *created by xiuer on
 *remark:  Android 的 ContentProvider，主要用于初始化应用程序上下文和一些全局功能
 * 1.利用 ContentProvider 自动初始化的特性，在应用启动时执行初始化代码
 *
 * 2.提供全局 Application 访问，避免单例模式或静态变量可能的内存泄漏问题
 *
 * 3.集中管理全局功能，如网络监听、生命周期监控等
 **/

val appContext: Application by lazy { Ktx.app }


/**
 * 自定义的 ContentProvider，主要目的不是提供数据，而是利用 ContentProvider 的初始化机制在应用启动时自动执行初始化代码
 * 关键点：
 * 为什么用 ContentProvider？
 *
 * Android 会自动初始化所有在 Manifest 中注册的 ContentProvider
 *
 * 它比 Application 的 onCreate() 更早执行，适合做早期初始化
 */
class Ktx : ContentProvider() {

    companion object {
        lateinit var app: Application
        private var mNetworkStateReceive: NetworkStateReceive? = null
        var watchActivityLife = true
        var watchAppLife = true
    }

    override fun onCreate(): Boolean {
        val application = context!!.applicationContext as Application
        install(application)
        return true
    }

    private fun install(application: Application) {
        app = application
        mNetworkStateReceive = NetworkStateReceive()
        app.registerReceiver(
            mNetworkStateReceive,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        if (watchActivityLife) application.registerActivityLifecycleCallbacks(KtxLifeCycleCallBack())
        if (watchAppLife) ProcessLifecycleOwner.get().lifecycle.addObserver(KtxAppLifeObserver)
    }


    /**
     * 这些方法都是空实现，因为这个 ContentProvider 的主要目的不是提供数据，而是利用其初始化机制
     */
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? = null

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun getType(uri: Uri): String? = null
}