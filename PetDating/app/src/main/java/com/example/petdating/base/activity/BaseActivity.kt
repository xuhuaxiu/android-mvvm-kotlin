package com.example.petdating.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 *created by xiuer on
 *remark:
 **/
abstract class BaseActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
    }


    /**
     * 获取当前页面的布局资源ID
     *
     * @return 布局资源ID
     */
    protected abstract fun getLayoutResId(): Int

}