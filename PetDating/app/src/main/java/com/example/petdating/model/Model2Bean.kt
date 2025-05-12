package com.example.petdating.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *created by xiuer on
 *remark: 列表数据
 **/
@Parcelize
data class Model2Bean(
    val label:  String? = null,
    val img:  Int? = null,
): Parcelable
