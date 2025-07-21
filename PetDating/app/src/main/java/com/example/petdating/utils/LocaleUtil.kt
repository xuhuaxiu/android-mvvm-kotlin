package com.example.petdating.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import java.util.Locale

/**
 *created by xiuer on
 *remark: 国际化多语言工具类
 **/

object LocaleUtil {

    /**
     * 获取当前系统语言
     */
    fun resetToSystemLanguage(context: Context): Context {
        // 获取系统语言
        val systemLocales = Resources.getSystem().configuration.locales

        // 应用系统语言
        val config = Configuration(context.resources.configuration)
        config.setLocales(systemLocales)
        Locale.setDefault(systemLocales[0])
        return context.createConfigurationContext(config)
    }


    /**
     * 手动设置应用语言
     * @param context 上下文对象
     * @param languageTag 语言标签，如 "zh-CN", "en-US", "ja-JP" 等
     * @return 应用了新语言配置的上下文
     */
    fun setAppLanguage(context: Context, languageTag: String): Context {
        // 将语言标签转换为Locale对象
        val locale =  Locale.forLanguageTag(languageTag)

        // 创建新的配置
        val config = Configuration(context.resources.configuration)

        // 设置指定语言
        config.setLocales(LocaleList(locale))

        // 更新默认语言
        Locale.setDefault(locale)

        // 创建并返回新上下文
        return context.createConfigurationContext(config)
    }

    // 扩展函数版本
    // fun Context.setAppLanguage(languageTag: String): Context = setAppLanguage(this, languageTag)
}
