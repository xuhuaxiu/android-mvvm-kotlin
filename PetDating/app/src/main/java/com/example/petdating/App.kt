package com.example.petdating
import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.tencent.mmkv.MMKV

/**
 *created by xiuer on
 *remark: 项目Application, APP启动时就需要初始化的数据在这里做
 **/
 class App : Application(), ViewModelStoreOwner {

    companion object {
        //等价于 Java 的 volatile 关键字，确保 _instance 变量可见性和禁止指令重排序。多线程环境下，一个线程对 _instance 的修改会立即被其他线程感知，避免因缓存导致的状态不一致。
        @Volatile private var _instance: App? = null
        val instance: App
            get() = _instance ?: throw IllegalStateException("App not initialized")
    }

    private val mAppViewModelStore by lazy { ViewModelStore() }

    override val viewModelStore: ViewModelStore
        get() = mAppViewModelStore


    override fun onCreate() {
        super.onCreate()
        _instance = this // 将当前实例赋值给 _instance
        initData()
    }


    private fun initData() {
        MMKV.initialize(this)
    }

    /**
     * 获取一个全局的ViewModel
     */
    private val mFactory: ViewModelProvider.Factory by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(this)
    }
    fun getAppViewModelProvider() = ViewModelProvider(this, mFactory)

}
