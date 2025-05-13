package com.example.petdating.ui.fragment.tab

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.example.petdating.R
import com.example.petdating.base.fragment.TabFragment
import com.example.petdating.databinding.AppLayoutMyInfoItemBinding
import com.example.petdating.databinding.FragmentMeBinding
import com.example.petdating.ext.init
import com.example.petdating.ext.parseState
import com.example.petdating.ext.visibleOrGone
import com.example.petdating.model.Model2Bean
import com.example.petdating.utils.CacheUtil
import com.example.petdating.viewmodel.MeViewModel

class MeFragment : TabFragment<MeViewModel, FragmentMeBinding>() {

    override fun layoutId(): Int = R.layout.fragment_me

    override fun initView(savedInstanceState: Bundle?) {
        initUnmLoginData() // 没接口，写点moc数据
        mDatabind.refresh.init {
            refreshData()
            mDatabind.refresh.isRefreshing = false
        }

    }

    // 刷新后获取列表数据，用户名字，图片
    private fun refreshData() {
        val isLogin = CacheUtil.isLogin()
        LogUtils.d("xiuer-------------> $isLogin")
        mDatabind.headLogin.root.visibleOrGone(isLogin)
        mDatabind.headUnLogin.root.visibleOrGone(!isLogin)
        if (isLogin) mViewModel.refreshMe()
    }

    private fun initUnmLoginData() {
        val list: List<Model2Bean> = listOf(
            Model2Bean(label = "item1", img = R.mipmap.app_image_icon_02),
            Model2Bean(label = "item2", img = R.mipmap.app_image_icon_02),
            Model2Bean(label = "item3", img = R.mipmap.app_image_icon_02),
            Model2Bean(label = "item4", img = R.mipmap.app_image_icon_02),
            Model2Bean(label = "item5", img = R.mipmap.app_image_icon_02)
        )
        for (item in list) {
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
                    { // 成功
                        LogUtils.d("xiuer-------------> success")
                        mDatabind.headLogin.tvAppInfoPhoneNumber.text = it.mobile
                    },
                    { // 失败
                        LogUtils.d("xiuer-------------> fail")

                    })
            }
        )

        mViewModel.model2Result.observe(
            this,
            Observer { it ->
                parseState(
                    it,
                    { // 成功

                        mDatabind.llColumn.removeAllViews()
                        for (rsp in it) {
                            addItem(rsp)
                        }
                    },
                    { // 失败

                    })
            }
        )

        mViewModel.model3Result.observe(
            this,
            Observer { it ->
                parseState(it,
                    { // 成功

                    },
                    { // 失败

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
        itemBinding.root.setOnClickListener {
            // 根据item.type处理不同的点击事件
        }

        // 4. 将子布局添加到父容器
        mDatabind.llColumn.addView(itemBinding.root)
    }


}