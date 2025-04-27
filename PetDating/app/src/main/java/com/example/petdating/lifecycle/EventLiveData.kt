package com.example.petdating.lifecycle

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.Timer
import java.util.TimerTask

/**
 *created by xiuer on
 *remark: 自定义的 MutableLiveData 实现，主要用于处理事件（Event）类型的数据，解决了普通 LiveData 在观察者重新注册时可能重复接收旧数据的问题（数据倒灌问题）
 **/
class EventLiveData<T> : MutableLiveData<T>() {

    private var isCleaning = false    // 是否正在清理状态
    private var hasHandled = true    // 事件是否已被处理
    private var isDelaying = false   // 是否处于延迟处理状态
    private var DELAY_TO_CLEAR_EVENT = 1000 // 事件自动清理延迟时间(ms)
    private val mTimer = Timer()        // 用于定时清理的计时器
    private var mTask: TimerTask? = null    // 定时任务
    private var isAllowNullValue = false    // 是否允许null值
    private var isAllowToClear = true    // 是否允许自动清理

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
     * 重写的 setValue 方法，默认不接收 null
     * 可通过 Builder 配置允许接收
     * 可通过 Builder 配置消息延时清理的时间
     *
     * @param value
     */
    override fun setValue(value: T?) {
        if (!isAllowNullValue && value == null && !isCleaning) {
            return
        }
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
        if (isAllowToClear) {
            isCleaning = true;
            super.postValue(null);
        } else {
            hasHandled = true;
            isDelaying = false;
        }
    }

    class Builder<T> {
        /**
         * 消息的生存时长 默认1秒
         */
        private var eventSurvivalTime = 1000

        /**
         * 是否允许传入 null value
         */
        private var isAllowNullValue = false

        /**
         * 是否允许自动清理，默认 true
         */
        private var isAllowToClear = true

        fun setEventSurvivalTime(eventSurvivalTime: Int): Builder<T> {
            this.eventSurvivalTime = eventSurvivalTime
            return this
        }

        fun setAllowNullValue(allowNullValue: Boolean): Builder<T> {
            isAllowNullValue = allowNullValue
            return this
        }

        fun setAllowToClear(allowToClear: Boolean): Builder<T> {
            isAllowToClear = allowToClear
            return this
        }

        fun create(): EventLiveData<T> {
            val liveData: EventLiveData<T> = EventLiveData()
            liveData.DELAY_TO_CLEAR_EVENT = eventSurvivalTime
            liveData.isAllowNullValue = isAllowNullValue
            liveData.isAllowToClear = this.isAllowToClear;
            return liveData
        }
    }
}