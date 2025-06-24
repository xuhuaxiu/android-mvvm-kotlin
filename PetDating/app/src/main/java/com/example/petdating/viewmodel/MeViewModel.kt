package com.example.petdating.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.example.petdating.R
import com.example.petdating.base.viewmodel.BaseViewModel
import com.example.petdating.ext.requestAsync
import com.example.petdating.model.Model1Bean
import com.example.petdating.model.Model2Bean
import com.example.petdating.model.Model3Bean
import com.example.petdating.network.ResultState
import com.example.petdating.repository.MockMeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class MeViewModel : BaseViewModel() {

    val list: List<Model2Bean> = listOf(
        Model2Bean(label = "item1", img = R.mipmap.app_image_icon_02),
        Model2Bean(label = "item2", img = R.mipmap.app_image_icon_02),
        Model2Bean(label = "item3", img = R.mipmap.app_image_icon_02),
        Model2Bean(label = "item4", img = R.mipmap.app_image_icon_02),
        Model2Bean(label = "item5", img = R.mipmap.app_image_icon_02)
    )

    //  private val repository = MeRepository()
   private val repository = MockMeRepository()

    private val _model1Result = MutableLiveData<ResultState<Model1Bean>>()
    val model1Result: LiveData<ResultState<Model1Bean>> = _model1Result

    private val _model2Result = MutableLiveData<ResultState<List<Model2Bean>>>()
    val  model2Result: LiveData<ResultState<List<Model2Bean>>> = _model2Result

    private val _model3Result = MutableLiveData<ResultState<Model3Bean>>()
    val  model3Result: LiveData<ResultState<Model3Bean>> = _model3Result

    //  刷新需要变更的数据
    fun refreshMe() {
        LogUtils.d("xiuer-------------> refreshMe")

        // 使用 viewModelScope 启动一个协程
        viewModelScope.launch {
            // 使用 supervisorScope 启动一个可并行子协程的作用域（即使其中一个失败，也不会取消其他）
            supervisorScope {
                LogUtils.d("xiuer-------------> supervisorScope")

                val step1 = async { repository.getBaseInfo() }
                val step2 = async { repository.getListInfo() }
                val step3 = async { repository.getOtherInfo() }
                requestAsync(step1, _model1Result)
                requestAsync(step2, _model2Result)
                requestAsync(step3, _model3Result)
            }
        }
    }

}