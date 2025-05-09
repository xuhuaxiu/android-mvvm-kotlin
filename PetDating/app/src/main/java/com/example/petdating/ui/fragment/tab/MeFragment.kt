package com.example.petdating.ui.fragment.tab

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.petdating.R
import com.example.petdating.base.fragment.TabFragment
import com.example.petdating.databinding.FragmentMeBinding
import com.example.petdating.ext.init
import com.example.petdating.ext.parseState
import com.example.petdating.utils.CacheUtil
import com.example.petdating.viewmodel.MeViewModel

class MeFragment : TabFragment<MeViewModel, FragmentMeBinding>() {

    override fun layoutId(): Int = R.layout.fragment_me

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.refresh.init {
            refreshData()
            mDatabind.refresh.isRefreshing = false
        }

    }

    // 刷新后获取列表数据，用户名字，图片
    private fun refreshData() {
        val token = CacheUtil.getToken()

        if (token == "") {
            CacheUtil.getToken()
        }else {
//            mDatabind.head_login.visibleOrGone(true)
//            mDatabind.head_unlogin.visibleOrGone(false)
            mViewModel.refreshMe()
        }
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.model1Result.observe(
            this,
            Observer { it ->
                parseState(it,
                    { // 成功


                    },
                    { // 失败

                    })
            }
        )

        mViewModel.model2Result.observe(
            this,
            Observer { it ->
                parseState(it,
                    { // 成功


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

}