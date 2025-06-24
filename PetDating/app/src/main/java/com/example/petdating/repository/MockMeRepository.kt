package com.example.petdating.repository

import com.blankj.utilcode.util.LogUtils
import com.example.petdating.R
import com.example.petdating.model.Model1Bean
import com.example.petdating.model.Model2Bean
import com.example.petdating.model.Model3Bean
import com.example.petdating.network.ApiResponse
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 *created by xiuer on
 *remark: 直通接口
 **/
class MockMeRepository {
    private val mockDelay = 500L // 模拟网络延迟(ms)
    private val successCode = 0 // 根据你定义的 success code

     suspend fun getBaseInfo(): ApiResponse<Model1Bean> {
         LogUtils.d("xiuer-------------> getBaseInfo")

        delay(mockDelay) // 模拟网络延迟
        return ApiResponse(
            method = "getBaseInfo",
            result = successCode,
            message = "模拟请求成功",
            data = Model1Bean(
                name = "模拟用户",
                img = R.mipmap.facai,
                mobile = "13800138000"
            )
        )
    }

     suspend fun getListInfo(): ApiResponse<List<Model2Bean>> {
         LogUtils.d("xiuer-------------> getListInfo")
         delay(mockDelay) // 模拟网络延迟

        return ApiResponse(
            method = "getListInfo",
            result = successCode,
            message = "模拟列表获取成功",
            data = listOf(
                Model2Bean(label = "消息通知", img = R.mipmap.app_image_icon_02),
                Model2Bean(label = "关于我们", img = R.mipmap.app_image_icon_02),
                Model2Bean(label = "帮助中心", img = R.mipmap.app_image_icon_02),
                Model2Bean(label = "隐私协议", img = R.mipmap.app_image_icon_02),
                Model2Bean(label = "语言设置", img = R.mipmap.app_image_icon_02)
            )
        )
    }

     suspend fun getOtherInfo(): ApiResponse<Model3Bean> {
         LogUtils.d("xiuer-------------> getOtherInfo")
         delay(mockDelay) // 模拟网络延迟

        return ApiResponse(
            method = "getOtherInfo",
            result = successCode,
            message = "模拟详情获取成功",
            data = Model3Bean(
                label1 = "交朋友",
                label2 = "找对象",
                label3 = "花大钱"
            )
        )
    }

    // 模拟错误响应示例
    private fun mockErrorResponse(methodName: String): ApiResponse<Nothing?> {
        return ApiResponse(
            method = methodName,
            result = -1, // 非0表示失败
            message = "模拟请求失败",
            data = null
        )
    }
    // 模拟带概率的随机响应（可选）
    suspend fun getRandomResponse(): ApiResponse<Model1Bean?> {
        return if (Random.nextBoolean()) {
            ApiResponse(
                method = "randomRequest",
                result = successCode,
                message = "随机成功",
                data = Model1Bean(name = "随机用户", img = null, mobile = null)
            )
        } else {
            ApiResponse(
                method = "randomRequest",
                result = -1,
                message = "随机失败",
                data = null
            )
        }
    }

}