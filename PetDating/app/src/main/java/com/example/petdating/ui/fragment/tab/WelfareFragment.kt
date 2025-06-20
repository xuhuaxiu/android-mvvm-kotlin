package com.example.petdating.ui.fragment.tab

import android.os.Bundle
import android.view.Gravity
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.petdating.R
import com.example.petdating.base.fragment.TabFragment
import com.example.petdating.databinding.FragmentWelfareBinding
import com.example.petdating.viewmodel.WelfareViewModel

class WelfareFragment : TabFragment<WelfareViewModel, FragmentWelfareBinding>() {

    override fun layoutId(): Int = R.layout.fragment_welfare

    override fun initView(savedInstanceState: Bundle?) {
        // 绑定ui与viewmodel,包括数据和点击事件
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()
    }


    // 点击事件这么写
    //注意： 对外部类的对象使用弱引用，避免内存泄漏
    inner class ProxyClick {
        fun check() {
            ToastUtils.showLong("xiuer-------> click check()")
            LogUtils.d("xiuer-------> click check()")
        }
    }

}