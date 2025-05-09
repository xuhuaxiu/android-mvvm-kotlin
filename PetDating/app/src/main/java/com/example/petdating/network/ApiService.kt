package com.example.petdating.network

import com.example.petdating.model.Model1Bean
import com.example.petdating.model.Model2Bean
import com.example.petdating.model.Model3Bean
import retrofit2.http.Body
import retrofit2.http.Header

/**
 *created by xiuer on
 *remark: 网络api
 **/
interface ApiService {
    companion object {
        var SERVER_URL = "https://baidu.com"
    }


    /**
     * 基础信息
     */
    // @Headers("Content-Type:application/jose; charset=utf-8")
    // @POST("接口")
    suspend fun getBaseInfo(
        @Header("Authorization") Authorization: String,
        @Body map: Map<String, @JvmSuppressWildcards Any>
    ): ApiResponse<Model1Bean>

    /**
     * 列表信息
     */
    // @Headers("Content-Type:application/jose; charset=utf-8")
    // @POST("接口")
    suspend fun getListInfo(
        @Header("Authorization") Authorization: String,
        @Body map: Map<String, @JvmSuppressWildcards Any>
    ): ApiResponse<Model2Bean>

    /**
     * 列表信息
     */
    // @Headers("Content-Type:application/jose; charset=utf-8")
    // @POST("接口")
    suspend fun getOtherInfo(
        @Header("Authorization") Authorization: String,
        @Body map: Map<String, @JvmSuppressWildcards Any>
    ): ApiResponse<Model3Bean>

}