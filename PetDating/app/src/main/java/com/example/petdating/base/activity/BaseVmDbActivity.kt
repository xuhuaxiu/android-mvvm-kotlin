package com.example.petdating.base.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.petdating.R
import com.example.petdating.base.viewmodel.BaseViewModel

/**
 *created by xiuer on
 *remark: 包含ViewModel 和 Databind, ViewModelActivity基类，把ViewModel 和Databind注入进来了
 * 需要使用Databind的清继承它,不需要就直接继承 BaseVmActivity
 **/
abstract class BaseVmDbActivity<VM: BaseViewModel, DB: ViewDataBinding> : BaseVmActivity<VM>() {
    lateinit var mDatabind: DB
    override fun onCreate(savedInstanceState: Bundle?) {
        userDataBinding(true)
        // 锁定方向
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)

    }

    /**
     * 创建DataBinding
     */
    override fun initDataBind() {
        mDatabind = DataBindingUtil.setContentView(this, layoutId())
        mDatabind.lifecycleOwner = this
    }
}