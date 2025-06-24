package com.example.petdating.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *created by xiuer on
 *remark: 基础信息
 **/
@Parcelize
data class Model1Bean(
    val name:  String? = null,
    val img:  Int? = null,
    val mobile:  String? = null
): Parcelable
