package com.example.petdating.base.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.petdating.R
import com.example.petdating.base.viewmodel.BaseViewModel
import com.example.petdating.ext.getVmClazz
import com.example.petdating.model.NetState
import com.example.petdating.utils.NetworkStateManager
import com.gyf.immersionbar.ImmersionBar


/**
 *created by xiuer on
 *remark: ViewModelActivity基类，把ViewModel注入进来了
 **/
abstract class BaseVmActivity<VM: BaseViewModel> : AppCompatActivity() {

    /**
     * 是否需要使用DataBinding 供子类BaseVmDbActivity修改，用户请慎动
     */
    private var isUserDb = false

    lateinit var mViewModel: VM

    abstract fun isFullScreen() :Boolean

    abstract fun layoutId() :Int

    abstract fun viewId() : View

    /**
     * 初始化视图相关
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 创建LiveData数据观察者
     */
    abstract fun createObserver()

    abstract fun showLoading(message: String = "请求中")

    abstract fun dismissLoading()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isFullScreen()) fullScreen()
        super.onCreate(savedInstanceState)
        // 根据是否使用DataBinding 以不同方式加载布局
        if (!isUserDb) {
            setContentView(layoutId())
        } else {
            initDataBind()
        }
//        enableEdgeToEdge()
//        ViewCompat.setOnApplyWindowInsetsListener(viewId()) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        init(savedInstanceState)
    }

   /**
    * 全屏属性需要在setContentView之前执行
    * 移除“半透明”状态栏
    * 设置布局全屏延伸到状态栏下
    * 设置状态栏背景色为透明
    */
   private fun fullScreen() {
       ImmersionBar.with(this).transparentStatusBar().statusBarColor(R.color.transparent).init()

/*       window.apply {
           decorView.systemUiVisibility = (
                   View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                           or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                   )
           statusBarColor = Color.TRANSPARENT
       }*/
   }

    private fun init(savedInstanceState: Bundle?) {
        mViewModel = createViewModel()
        registerUiChange()
        initView(savedInstanceState)
        createObserver()
        NetworkStateManager.networkState.observe(this, Observer {
            onNetworkStateChanged(it)
        }
        )
    }

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    /**
     * 注册 UI 事件
     */
    private fun registerUiChange() {
        //显示弹窗
        mViewModel.loadingChange.showDialog.observe(this,Observer{
            showLoading(
                if (it.isEmpty()) {
                    "请求中..."
                } else it
            )
        })
        //关闭弹窗
        mViewModel.loadingChange.dismissDialog.observe(this, Observer {
            dismissLoading()
        })
    }


    fun userDataBinding(isUserDb: Boolean) {
        this.isUserDb = isUserDb
    }
    /**
     * 供子类BaseVmDbActivity 初始化 Databinding 操作
     */
    open fun initDataBind() {}

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(netState: NetState) {}

}