package com.example.petdating.ext

import java.lang.reflect.ParameterizedType

/**
 *created by xiuer on
 *remark: 顶层文件， 拓展view model 相关方法
 **/



/**
 * 获取当前类绑定的泛型ViewModel-clazz
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getVmClazz(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}