package com.example.petdating.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.blankj.utilcode.util.ToastUtils
import com.example.petdating.R
import com.example.petdating.base.activity.BaseActivity
import com.example.petdating.databinding.ActivityMainBinding
import com.example.petdating.utils.CacheUtil
import com.example.petdating.viewmodel.MainViewModel
import java.util.ArrayList

class MainActivity: BaseActivity<MainViewModel, ActivityMainBinding>() {
    var exitTime = 0L

    override fun layoutId(): Int = R.layout.activity_main

    override fun viewId(): View = findViewById(R.id.main)

    override fun isFullScreen(): Boolean = true

    override fun initView(savedInstanceState: Bundle?) {

       onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
           override fun handleOnBackPressed() {
               val nav = Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)
               if (nav.currentDestination != null && nav.currentDestination!!.id != R.id.mainFragment) {
                   // 如果当前界面不是主页，那么直接调用返回即可
                   nav.navigateUp()
               } else {
                   // 是主页, 两秒内再次点击退出
                   if (System.currentTimeMillis() - exitTime > 2000) {
                       ToastUtils.showShort(getString(R.string.exit_app_message))
                       exitTime = System.currentTimeMillis()
                   } else {
                       finish()
                   }
               }
           }

       })
        iniData()
    }

    private fun iniData() {
        CacheUtil.setIsLogin(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}