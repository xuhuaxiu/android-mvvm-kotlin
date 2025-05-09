package com.example.petdating.utils

import android.os.Build
import org.json.JSONObject


/**
 *created by xiuer on
 *remark: 通用请求参数z在这里面封装
 **/
object BaseNetUtil {

    /**
     * 构建通用参数
     * @param params  JsonObject
     * @return HashMap
     */
    fun buildCommonParams(params: JSONObject): HashMap<String, Any>{
        val map = HashMap<String, Any>()
        map["programmingLanguage"] = "Kotlin"
        map["brand"] = Build.BRAND
        map["time"] = System.currentTimeMillis().toString() + ""
        return map
    }

    /**
     * 构建通用参数
     * @param params   String
     * @return HashMap
     */
    fun buildCommonParams(params: String): HashMap<String, Any>{
        val map = HashMap<String, Any>()
        map["brand"] = Build.BRAND
        map["time"] = System.currentTimeMillis().toString() + ""
        return map
    }

}