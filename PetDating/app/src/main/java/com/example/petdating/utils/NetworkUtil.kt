package com.example.petdating.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import java.io.IOException
import java.net.HttpURLConnection
import java.net.NetworkInterface
import java.net.SocketException
import java.net.URL

/**
 *created by xiuer on
 *remark: 网络工具类
 **/
object NetworkUtil {
    // 测试连接的URL
    var url = "http://www.baidu.com"

    /**
     *  网络状态常量
     */
    const val NET_CNNT_BAIDU_OK = 1 // 网络正常

    const val NET_CNNT_BAIDU_TIMEOUT = 2  // 网络连接超时

    const val NET_NOT_PREPARE = 3  // 网络未准备好

    const val NET_ERROR = 4 // 网络错误

    private const val TIMEOUT = 3000 // 连接超时时间(毫秒)

    /**
     * 检查网络是否可用
     * @param context 上下文
     * @return Boolean 网络是否可用
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val manager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return manager?.activeNetworkInfo?.isAvailable ?: false
    }

    /**
     * 获取本地IP地址
     * @return String IP地址
     */
    fun getLocalIpAddress(): String {
        var ret = ""
        try {
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddr = intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress) {
                        ret = inetAddress.hostAddress.toString()
                    }
                }
            }
        } catch (ex: SocketException) {
            ex.printStackTrace()
        }
        return ret
    }

    /**
     * 获取当前网络状态
     * @param context 上下文
     * @return Int 网络状态常量
     */
    fun getNetState(context: Context): Int {
        try {
            val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (connectivity != null) {
                val networkinfo = connectivity.activeNetworkInfo
                if (networkinfo != null) {
                    if (networkinfo.isAvailable && networkinfo.isConnected) {
                        return if (!connectionNetwork()) {
                            NET_CNNT_BAIDU_TIMEOUT
                        } else {
                            NET_CNNT_BAIDU_OK
                        }
                    } else {
                        return NET_NOT_PREPARE
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return NET_ERROR
    }

    /**
     * 测试网络连接(访问百度)
     * @return Boolean 是否连接成功
     */
    private fun connectionNetwork(): Boolean {
        var result = false
        var httpUrl: HttpURLConnection? = null
        try {
            httpUrl = URL(url).openConnection() as HttpURLConnection
            httpUrl.connectTimeout = TIMEOUT
            httpUrl.connect()
            result = true
        } catch (e: IOException) {
            // Ignored
        } finally {
            httpUrl?.disconnect()
        }
        return result
    }

    /**
     * 检查是否为3G网络
     * @param context 上下文
     * @return Boolean 是否为3G
     */
    @RequiresApi(Build.VERSION_CODES.M)
    fun is3G(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return false
        val network = connectivityManager.activeNetwork ?: return false
        val caps = connectivityManager.getNetworkCapabilities(network) ?: return false

        // 通过带宽等特征推断3G
        return caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) &&
                !caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                caps.linkDownstreamBandwidthKbps < 10_000 // 3G典型带宽
    }

    /**
     * 检查是否为WiFi网络
     * @param context 上下文
     * @return Boolean 是否为WiFi
     */
    fun isWifiConnected(context: Context): Boolean {
        val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        return connManager?.activeNetworkInfo?.run {
            isConnected && type == ConnectivityManager.TYPE_WIFI
        } ?: false
    }
    /**
     * 检查是否为2G网络
     * @param context 上下文
     * @return Boolean 是否为2G
     */
    fun is2G(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo != null && (activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_EDGE ||
                        activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_GPRS ||
                        activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_CDMA)
    }

    /**
     * 检查WiFi是否开启
     * @param context 上下文
     * @return Boolean WiFi是否开启
     */
    fun isWifiEnabled(context: Context): Boolean {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager
        return wifiManager?.isWifiEnabled ?: false
    }
}