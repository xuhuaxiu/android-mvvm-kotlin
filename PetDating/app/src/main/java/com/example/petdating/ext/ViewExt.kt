package com.example.petdating.ext

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.petdating.ui.fragment.tab.HomeFragment
import com.example.petdating.ui.fragment.tab.MeFragment
import com.example.petdating.ui.fragment.tab.WelfareFragment

/**
 *created by xiuer on
 *remark: view 相关的顶层方法
 **/

/**
 * 初始化首页面tag页面
 */
fun ViewPager2.initMain(fragment: Fragment): ViewPager2 {
    //是否可滑动
    this.isUserInputEnabled = false

    //设置适配器
    adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    HomeFragment()
                }
                1 -> {
                    WelfareFragment()
                }
                2 ->{
                    MeFragment()
                }
                else -> {
                    HomeFragment()
                }
            }
        }
        override fun getItemCount() = 3
    }
    return this
}

/**
 * 初始化首页面tag按钮
 */
