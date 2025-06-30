package com.example.petdating.base.activity

import android.content.res.Resources
import android.os.Bundle
import android.os.Looper
import androidx.databinding.ViewDataBinding
import com.example.petdating.base.viewmodel.BaseViewModel
import com.example.petdating.utils.LoadingDialog
import me.jessyan.autosize.AutoSizeCompat
import me.jessyan.autosize.AutoSizeConfig

/**
 *created by xiuer on
 *remark: 项目中的Activity基类，在这里实现显示弹窗，吐司，还有加入自己的需求操作 ，如果不想用Databind，请继承
 *  * BaseVmActivity例如
 *  * abstract class BaseActivity<VM : BaseViewModel> : BaseVmActivity<VM>()
 **/
abstract class BaseActivity<VM: BaseViewModel, DB: ViewDataBinding>: BaseVmDbActivity<VM, DB>() {


    abstract override fun layoutId(): Int

    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 创建liveData观察者
     */
    override fun createObserver() {}

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        LoadingDialog.show(supportFragmentManager)
    }
    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        LoadingDialog.dismiss(supportFragmentManager)

    }

    /**
     * 在任何情况下本来适配正常的布局突然出现适配失效，适配异常等问题，只要重写 Activity 的 getResources() 方法
     */
    override fun getResources(): Resources {
        this.runOnUiThread {
            val designWidth = AutoSizeConfig.getInstance().screenWidth
            val designHeight = AutoSizeConfig.getInstance().screenHeight
            val isBaseOnWidth = designWidth <= designHeight
            if(Looper.myLooper() == Looper.getMainLooper()){
                AutoSizeCompat.autoConvertDensity(super.getResources(), 360f, isBaseOnWidth)
            }
        }
        return super.getResources()
    }
}