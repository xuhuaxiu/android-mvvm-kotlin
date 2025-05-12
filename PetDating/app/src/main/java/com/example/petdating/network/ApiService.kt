package com.example.petdating.network

import com.example.petdating.model.Model1Bean
import com.example.petdating.model.Model2Bean
import com.example.petdating.model.Model3Bean
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 *created by xiuer on
 *remark: 网络api, 理论上是请求接口拿数据，但是我没有写接口，所以获取不到数据
 **/
interface ApiService {
    companion object {
        var SERVER_URL = "https://baidu.com"
    }


    /**
     * 基础信息
     */
     @Headers("Content-Type:application/jose; charset=utf-8")
     @POST("/baseInfo")
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
    ): ApiResponse<List<Model2Bean>>

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