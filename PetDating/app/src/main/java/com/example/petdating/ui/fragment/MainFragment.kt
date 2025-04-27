package com.example.petdating.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
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
        // mDatabind.mainViewpager.initMain(this)
    }
}