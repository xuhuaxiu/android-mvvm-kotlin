package com.example.petdating.ui.fragment.tab

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.example.petdating.R
import com.example.petdating.base.fragment.TabFragment
import com.example.petdating.databinding.AppLayoutMyInfoItemBinding
import com.example.petdating.databinding.FragmentMeBinding
import com.example.petdating.ext.clickNoRepeat
import com.example.petdating.ext.init
import com.example.petdating.ext.parseState
import com.example.petdating.ext.visibleOrGone
import com.example.petdating.model.Model2Bean
import com.example.petdating.utils.CacheUtil
import com.example.petdating.viewmodel.MeViewModel
import com.xhqb.jetpackmvvm.base.appContext

class MeFragment : TabFragment<MeViewModel, FragmentMeBinding>() {


    override fun layoutId(): Int = R.layout.fragment_me


    override fun initView(savedInstanceState: Bundle?) {

        refreshData()
        // 等价于直接设置监听：mDatabind.refresh.setOnRefreshListener
        mDatabind.refresh.init {
            refreshData()
            mViewModel.updateFirstName()
            mDatabind.refresh.isRefreshing = false
        }

    }



    // 刷新后获取列表数据，用户名字，图片
    private fun refreshData() {
        val isLogin = CacheUtil.isLogin()
        LogUtils.d("xiuer-------------> 登录没 $isLogin")
        mDatabind.headLogin.root.visibleOrGone(isLogin)
        mDatabind.headUnLogin.root.visibleOrGone(!isLogin)
        if (isLogin) mViewModel.refreshMe() else  initUnmLoginData()
    }


    // // 未登录时，没接口，写点moc数据
    private fun initUnmLoginData() {
        for (item in mViewModel.list) {
            addItem(item)
        }
    }


    override fun createObserver() {
        super.createObserver()
        mViewModel.model1Result.observe(
            this,
            Observer { it ->
                parseState(
                    it,
                    {
                        LogUtils.d("xiuer-------------> model1Result success")
                        mDatabind.headLogin.tvAppInfoName.text = it.name
                        mDatabind.headLogin.tvAppInfoPhoneNumber.text = it.mobile
                        Glide.with(this).load(it.img).into(mDatabind.headLogin.tvAppInfoImg)
                    },
                    {
                        LogUtils.d("xiuer-------------> model1Result fail")
                    })
            }
        )

        mViewModel.model2Result.observe(
            this,
            Observer { it ->
                parseState(
                    it,
                    {
                        LogUtils.d("xiuer------------->model2Result  ${it}")
                        mDatabind.llColumn.removeAllViews()
                        for (rsp in it) {
                            addItem(rsp)
                        }
                    },
                    {
                        LogUtils.d("xiuer-------------> model2Result fail")
                    })
            }
        )

        mViewModel.model3Result.observe(
            this,
            Observer { it ->
                parseState(it,
                    {
                        mDatabind.headLogin.tvMyInfoOne.text=it.label1
                        mDatabind.headLogin.tvMyInfoTwo.text=it.label2
                        mDatabind.headLogin.tvMyInfoThree.text=it.label3
                        LogUtils.d("xiuer-------------> model3Result ${it}")
                    },
                    {
                        LogUtils.d("xiuer------------->model3Result  fail")
                    })
            }
        )
    }


    private fun addItem(rsp: Model2Bean) {
        // 1. 使用 ViewBinding 加载子布局
        val itemBinding = AppLayoutMyInfoItemBinding.inflate(
            LayoutInflater.from(activity),
            mDatabind.llColumn,
            false
        )

        // 2. 设置数据
        itemBinding.tvItemLabel.text = rsp.label
        Glide.with(this).load(rsp.img).into(itemBinding.ivIcon)

        // 3. 设置点击事件
        itemBinding.root.clickNoRepeat {
            // 根据item.type处理不同的点击事件
            val label: String? = rsp.label
            LogUtils.d("xiuer------------->click  ${label}")
            ToastUtils.showLong("you click  ${label}")

        }

        // 4. 将子布局添加到父容器
        mDatabind.llColumn.addView(itemBinding.root)
    }


}