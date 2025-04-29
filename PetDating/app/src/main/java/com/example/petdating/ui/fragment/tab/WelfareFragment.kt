package com.example.petdating.ui.fragment.tab

import android.os.Bundle
import com.example.petdating.R
import com.example.petdating.base.fragment.TabFragment
import com.example.petdating.databinding.FragmentWelfareBinding
import com.example.petdating.viewmodel.WelfareViewModel

class WelfareFragment : TabFragment<WelfareViewModel, FragmentWelfareBinding>() {

    override fun layoutId(): Int = R.layout.fragment_welfare

    override fun initView(savedInstanceState: Bundle?) {
    }

}