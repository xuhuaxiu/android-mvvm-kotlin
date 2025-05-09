package com.example.petdating.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *created by xiuer on
 *remark: 列表数据
 **/
@Parcelize
data class Model2Bean(
    val label1:  String? = null,
    val label2:  String? = null,
    val label3:  String? = null,
    val label4:  String? = null,
    val label5:  String? = null,
    val label6:  String? = null
): Parcelable
