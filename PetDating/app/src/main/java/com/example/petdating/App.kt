package com.example.petdating
import android.app.Application
import com.tencent.mmkv.MMKV

/**
 *created by xiuer on
 *remark: 项目Application, APP启动时就需要初始化的数据在这里做
 **/
 class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        MMKV.initialize(this)
    }

}
