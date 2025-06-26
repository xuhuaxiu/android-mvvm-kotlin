package com.example.petdating.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.petdating.R
import com.example.petdating.base.activity.BaseActivity
import com.example.petdating.databinding.ActivitySplashBinding
import com.example.petdating.viewmodel.AppViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<AppViewModel,ActivitySplashBinding>() {

    override fun layoutId(): Int = R.layout.activity_splash

    override fun isFullScreen(): Boolean = true

    override fun viewId(): View =  findViewById(R.id.splash)

    override fun initView(savedInstanceState: Bundle?) {
        // 👇 5 秒后跳转到 MainActivity
        lifecycleScope.launch {
            delay(5000)
            startActivity()
            finish() // 可选：关闭 SplashActivity，防止返回
        }
    }

    private fun startActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

}