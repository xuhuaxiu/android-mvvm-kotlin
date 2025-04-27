package com.example.petdating.ui.fragment.tab

import android.os.Bundle
import com.example.petdating.R
import com.example.petdating.base.fragment.TabFragment
import com.example.petdating.databinding.FragmentHomeBinding
import com.example.petdating.viewmodel.HomeViewModel

class HomeFragment : TabFragment<HomeViewModel, FragmentHomeBinding>() {
    override fun layoutId(): Int = R.layout.fragment_home

    override fun initView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }


}