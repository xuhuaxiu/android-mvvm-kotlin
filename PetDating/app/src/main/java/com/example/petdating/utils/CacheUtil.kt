package com.example.petdating.utils

import android.text.TextUtils
import com.tencent.mmkv.MMKV

object CacheUtil {
    private const val appKey = "app"

    /**
     * 设置token信息
     */
    fun setToken(token: String?) {
        val kv = MMKV.mmkvWithID(appKey)
        if (token != null && token.isNotEmpty()) {
            kv.encode("pat_token", token)
        } else {
            kv.encode("pat_token", "")
        }
    }

    /**
     * 获取token信息
     */
    fun getToken(): String {
        val kv = MMKV.mmkvWithID(appKey)
        if (TextUtils.isEmpty(kv.decodeString("pat_token", ""))) {
            return ""
        }
        return "Bearer " + kv.decodeString("pat_token", "")
    }

    /**
     * 是否已经登录
     */
    fun isLogin(): Boolean {
        val kv = MMKV.mmkvWithID(appKey)
        return kv.decodeBool("login", false)
    }

    /**
     * 设置是否已经登录
     */
    fun setIsLogin(isLogin: Boolean) {
        val kv = MMKV.mmkvWithID(appKey)
        kv.encode("login", isLogin)
    }

}
