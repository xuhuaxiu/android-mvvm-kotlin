package com.example.petdating.ui.fragment.tab

import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.petdating.R
import com.example.petdating.base.fragment.TabFragment
import com.example.petdating.databinding.FragmentHomeBinding
import com.example.petdating.viewmodel.HomeViewModel
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator


class HomeFragment : TabFragment<HomeViewModel, FragmentHomeBinding>() {

    override fun layoutId(): Int = R.layout.fragment_home

    override fun initView(savedInstanceState: Bundle?) {
        // 初始化ui逻辑
        // 绑定ui与viewmodel,包括数据和点击事件
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()
        setupBanner()
    }
    private fun setupBanner() {
        mDatabind.banner.apply {
            setAdapter(object : BannerImageAdapter<Int>(mViewModel.bannerList) {
                override fun onBindView(
                    holder: BannerImageHolder,
                    data: Int,
                    position: Int,
                    size: Int
                ) {
                    holder.imageView.setImageResource(data)
                }
            })

            setIndicator(CircleIndicator(context))

            // 添加点击事件监听
            setOnBannerListener { data, position ->
                ToastUtils.showShort("点击了第 ${position + 1} 张图")
            }

            start()
        }
    }
    // 点击事件这么写
    //注意： 对外部类的对象使用弱引用，避免内存泄漏
    inner class ProxyClick {
    }

}