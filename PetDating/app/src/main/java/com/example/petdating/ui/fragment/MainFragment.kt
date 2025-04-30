package com.example.petdating.ui.fragment

import android.os.Bundle
import com.example.petdating.R
import com.example.petdating.base.fragment.TabFragment
import com.example.petdating.base.viewmodel.BaseViewModel
import com.example.petdating.databinding.FragmentMainBinding
import com.example.petdating.ext.initMain

/**
 *created by xiuer on
 *remark: 项目主页Fragment
 **/
class MainFragment: TabFragment<BaseViewModel, FragmentMainBinding>() {

    override fun layoutId(): Int = R.layout.fragment_main

    override fun initView(savedInstanceState: Bundle?) {
        // 初始化viewpager对应的fragment
        mDatabind.mainViewpager.initMain(this)

        // 初始化导航栏目
        mDatabind.mainBottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    mDatabind.mainViewpager.setCurrentItem(0,false)
                    true
                }
                R.id.menu_welfare -> {
                    mDatabind.mainViewpager.setCurrentItem(1,false)
                    true
                }
                R.id.menu_me -> {
                    mDatabind.mainViewpager.setCurrentItem(2,false)
                    true
                }
                else -> false
            }
        }

    }
}