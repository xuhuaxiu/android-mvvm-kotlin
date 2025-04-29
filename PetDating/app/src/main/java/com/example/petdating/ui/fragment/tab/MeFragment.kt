package com.example.petdating.ui.fragment.tab

import android.os.Bundle
import com.example.petdating.R
import com.example.petdating.base.fragment.TabFragment
import com.example.petdating.databinding.FragmentMeBinding
import com.example.petdating.viewmodel.MeViewModel

class MeFragment : TabFragment<MeViewModel, FragmentMeBinding>() {

    override fun layoutId(): Int = R.layout.fragment_me

    override fun initView(savedInstanceState: Bundle?) {
    }

}