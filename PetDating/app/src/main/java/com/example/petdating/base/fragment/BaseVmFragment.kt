package com.example.petdating.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
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
 *remark: ViewModelFragment基类，自动把ViewModel注入Fragment
 **/
abstract class BaseVmFragment<VM : BaseViewModel> : Fragment() {
    //是否第一次加载

    lateinit var mViewModel: VM

    lateinit var mActivity: AppCompatActivity

    private var isFirst: Boolean = true
    private var isFullScreen: Boolean = false

    abstract fun layoutId(): Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirst = true
        mViewModel = createViewModel()
        initView(savedInstanceState)
        createObserver()
        onVisible()
        registorDefUIChange()
        initData()

    }

    override fun onResume() {
        super.onResume()
        fullScreen()
    }

    open fun setFullScreen(isFull:Boolean){
        isFullScreen = isFull
    }

    private fun fullScreen() {
        if (isFullScreen) {
            ImmersionBar.with(this).transparentStatusBar().statusBarColor(R.color.transparent).init()
        }else {
            ImmersionBar.with(this).statusBarColor(R.color.colorPrimary).init()
        }
    }
    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 创建观察者
     */
    abstract fun createObserver()

    /**
     * 懒加载
     */
    abstract fun lazyLoadData()


    /**
     * Fragment执行onCreate后触发的方法
     */
    open fun initData() {}

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(netState: NetState) {}

    abstract fun showLoading(message: String = "请求中...")

    abstract fun dismissLoading()

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            //延迟加载0.12秒加载 避免fragment跳转动画和渲染ui同时进行，出现些微的小卡顿
            view?.postDelayed({
                lazyLoadData()
                //在Fragment中，只有懒加载过了才能开启网络变化监听
                if (view != null) {
                    NetworkStateManager.networkState.observe(viewLifecycleOwner, Observer {
                        //不是首次订阅时调用方法，防止数据第一次监听错误
                        if (!isFirst) {
                            onNetworkStateChanged(it)
                        }
                    })
                }
                isFirst = false
            }, 120)
        }
    }

    /**
     * 注册 UI 事件
     */
    private fun registorDefUIChange() {
        mViewModel.loadingChange.showDialog.observe(viewLifecycleOwner, Observer {
            showLoading()
        })
        mViewModel.loadingChange.dismissDialog.observe(viewLifecycleOwner, Observer {
            dismissLoading()
        })
    }
}