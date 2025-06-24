package com.example.petdating.repository

import com.example.petdating.model.Model1Bean
import com.example.petdating.model.Model2Bean
import com.example.petdating.model.Model3Bean
import com.example.petdating.network.ApiResponse
import com.example.petdating.network.apiService
import com.example.petdating.utils.BaseNetUtil
import com.example.petdating.utils.CacheUtil
import org.json.JSONObject

/**
 *created by xiuer on
 *remark: 获取真实数据，直通接口, 可以在此拼接接口请求人参
 **/
class MeRepository {

     suspend fun getBaseInfo(): ApiResponse<Model1Bean> {
        val token = CacheUtil.getToken()
        val body = BaseNetUtil.buildCommonParams(JSONObject())
        return apiService.getBaseInfo(token,body)

    }

    suspend fun getListInfo(): ApiResponse<List<Model2Bean>> {
        val token = CacheUtil.getToken()
        val body = BaseNetUtil.buildCommonParams(JSONObject())
        return apiService.getListInfo(token,body)

    }

    suspend fun getOtherInfo(): ApiResponse<Model3Bean> {
        val token = CacheUtil.getToken()
        val body = BaseNetUtil.buildCommonParams(JSONObject())
        return apiService.getOtherInfo(token,body)

    }
}