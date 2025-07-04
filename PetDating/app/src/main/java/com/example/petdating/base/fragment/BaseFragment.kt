package com.example.petdating.base.fragment

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.petdating.base.viewmodel.BaseViewModel
import com.example.petdating.utils.LoadingDialog

/**
 *created by xiuer on
 *remark:
 **/
abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbFragment<VM, DB>() {

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

    /**
     * Fragment执行onViewCreated后触发
     */
    override fun initData() { }

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        LoadingDialog.show(requireActivity().supportFragmentManager)
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        LoadingDialog.dismiss(requireActivity().supportFragmentManager)

    }
}