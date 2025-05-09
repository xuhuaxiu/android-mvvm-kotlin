package com.example.petdating.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *created by xiuer on
 *remark: 其他资料
 **/
@Parcelize
data class Model3Bean(
    val label1:  String? = null,
    val label2:  String? = null,
    val label3:  String? = null
): Parcelable
