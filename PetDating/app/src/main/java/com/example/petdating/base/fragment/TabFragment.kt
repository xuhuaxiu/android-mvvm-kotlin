package com.example.petdating.base.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.example.petdating.base.viewmodel.BaseViewModel
import com.example.petdating.databinding.FragmentMainBinding

/**
 *created by xiuer on
 *remark: 首页tab栏fragment的基类
 **/
abstract class TabFragment<VM: BaseViewModel, DB: ViewDataBinding>:  BaseFragment<VM, DB>() {

    /**
     * 当前Fragment绑定的视图布局
     */
    abstract override fun layoutId(): Int

    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 懒加载 只有当前fragment视图显示时才会触发该方法
     */
    override fun lazyLoadData() {}

    /**
     * 创建LiveData观察者 Fragment执行onViewCreated后触发
     */
    override fun createObserver() {}
}