package com.example.petdating.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.petdating.base.viewmodel.BaseViewModel
import com.example.petdating.ext.requestAsync
import com.example.petdating.model.Model1Bean
import com.example.petdating.model.Model2Bean
import com.example.petdating.model.Model3Bean
import com.example.petdating.network.ApiResponse
import com.example.petdating.network.ResultState
import com.example.petdating.network.apiService
import com.example.petdating.utils.BaseNetUtil
import com.example.petdating.utils.CacheUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import org.json.JSONObject

class MeViewModel : BaseViewModel() {

    val  model1Result = MutableLiveData<ResultState<Model1Bean>>()
    val  model2Result = MutableLiveData<ResultState<List<Model2Bean>>>()
    val  model3Result = MutableLiveData<ResultState<Model3Bean>>()


    //  刷新需要变更的数据
    fun refreshMe() {
        // 使用 viewModelScope 启动一个协程
        viewModelScope.launch {
            // 使用 supervisorScope 启动一个可并行子协程的作用域（即使其中一个失败，也不会取消其他）
            supervisorScope {
                val step1 = async { getModel1() }
                val step2 = async { getModel2() }
                val step3 = async { getModel3() }
                requestAsync(step1, model1Result)
                requestAsync(step2, model2Result)
                requestAsync(step3, model3Result)
            }
        }

    }

    private suspend fun getModel1(): ApiResponse<Model1Bean>{
        val token = CacheUtil.getToken()
        val body = BaseNetUtil.buildCommonParams(JSONObject())
        return apiService.getBaseInfo(token,body)

    }

    private suspend fun getModel2(): ApiResponse<List<Model2Bean>>{
        val token = CacheUtil.getToken()
        val body = BaseNetUtil.buildCommonParams(JSONObject())
        return apiService.getListInfo(token,body)

    }
    private suspend fun getModel3(): ApiResponse<Model3Bean>{
        val token = CacheUtil.getToken()
        val body = BaseNetUtil.buildCommonParams(JSONObject())
        return apiService.getOtherInfo(token,body)

    }
}