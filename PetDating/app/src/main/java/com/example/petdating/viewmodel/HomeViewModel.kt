package com.example.petdating.viewmodel

import androidx.lifecycle.ViewModel
import com.example.petdating.R
import com.example.petdating.base.viewmodel.BaseViewModel

class HomeViewModel : BaseViewModel() {
    // 图片资源列表
     val bannerList by lazy {
        listOf(
            R.mipmap.img_facai_banner_1,
            R.mipmap.img_facai_banner_2,
            R.mipmap.img_facai_banner_3
        )
    }

}