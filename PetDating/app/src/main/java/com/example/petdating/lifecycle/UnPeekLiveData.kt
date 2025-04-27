package com.example.petdating.lifecycle

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.Timer
import java.util.TimerTask

/**
 *created by xiuer on
 *remark: 避免数据倒灌的 LiveData（页面重建时不推送旧数据）
 **/
class UnPeekLiveData<T> : MutableLiveData<T>() {

    private var isCleaning = false    // 是否正在清理状态
    private var hasHandled = true    // 事件是否已被处理
    private var isDelaying = false   // 是否处于延迟处理状态
    private var DELAY_TO_CLEAR_EVENT = 1000 // 事件自动清理延迟时间(ms)
    private val mTimer = Timer()        // 用于定时清理的计时器
    private var mTask: TimerTask? = null    // 定时任务


    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            if (isCleaning) {
                hasHandled = true
                isDelaying = false
                isCleaning = false
                return@Observer
            }
            if (!hasHandled) {
                hasHandled = true
                isDelaying = true
                observer.onChanged(it)
            } else if (isDelaying) {
                observer.onChanged(it)
            }
        })
    }

    override fun observeForever(observer: Observer<in T>) {
        super.observeForever(Observer {
            if (isCleaning) {
                hasHandled = true
                isDelaying = false
                isCleaning = false
                return@Observer
            }
            if (!hasHandled) {
                hasHandled = true
                isDelaying = true
                observer.onChanged(it)
            } else if (isDelaying) {
                observer.onChanged(it)
            }
        })
    }

    /**
     * 重写的 setValue 方法
     * @param value
     */
    override fun setValue(value: T?) {
        hasHandled = false
        isDelaying = false
        super.setValue(value)
        mTask?.let {
            it.cancel()
            mTimer.purge()
        }
        mTask = object : TimerTask() {
            override fun run() {
                clear()
            }
        }
        mTimer.schedule(mTask, DELAY_TO_CLEAR_EVENT.toLong())
    }

    private fun clear() {
        hasHandled = true
        isDelaying = false
    }
}