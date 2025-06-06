package com.example.petdating.network

/**
 *created by xiuer on
 *remark: 错误枚举类
 **/

enum class Error(private val code: Int, private val err: String) {

    /**
     * 未知错误
     */
    UNKNOWN(1000, "请求失败，请稍后再试"),
    /**
     * 解析错误
     */
    PARSE_ERROR(1001, "解析错误，请稍后再试"),
    /**
     * 网络错误
     */
    NETWORK_ERROR(1002, "网络连接错误，请稍后重试"),

    /**
     * 证书出错
     */
    SSL_ERROR(1004, "证书出错，请稍后再试"),

    /**
     * 连接超时
     */
    TIMEOUT_ERROR(1006, "网络连接超时，请稍后重试"),

    /**
     * token过期
     */
    TOKEN_EXPROIRED(1007, "登录超时，请重新登录");

    fun getValue(): String {
        return err
    }

    fun getKey(): Int {
        return code
    }

}