package com.example.petdating.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.petdating.base.viewmodel.BaseViewModel

class WelfareViewModel : BaseViewModel() {
    val text = MutableLiveData<String>().apply {
        value = "This is test Hello welfafe"
    }


}