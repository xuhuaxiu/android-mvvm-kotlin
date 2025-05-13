
## 项目框架
* android-mvvm-kotlin
* base 封装的基类，类相关公共方法封装在这里
* ext 顶层方法，全部可以的方法封装在这里
* lifecycle jetpack 生命周期相关的数据及回调封装在这里
* navigation 导航相关
* network 网络相关，使用协程发起网络请求
* utils 工具类
* model数据相关；
* ui（view）视图相关； 
* viewModel，交互逻辑相关


## 技术要点
* 采用MVVM模式开发，视图操作和业务逻辑解耦
* 采用DataBinding框架实现View与数据的绑定，View层做到极度简洁
* 采用Lifecycle实现ViewModel的生命周期回调
* 采用Retrofit + RxJava实现网络请求
* 采用Glide实现图片加载


## 技术组合 MVVM + Retrofit + Coroutines + LiveData(如果UI需要持续更新数据流, 考虑使用Flow)
 * MVVM架构：通过 ViewModel 分离UI逻辑和业务逻辑
 * Retrofit：处理网络请求
 * Kotlin Coroutines：替代RxJava处理异步操作
 * LiveData：观察数据变化并更新UI
 * 结构化并发：通过 viewModelScope 和 supervisorScope 管理协程生命周期

## 优点
* 并行请求优化：使用 async 并发执行多个独立请求
* 错误隔离：supervisorScope 确保一个请求失败不影响其他
* 状态封装：ResultState 统一封装成功/失败/加载状态
* 生命周期安全：viewModelScope 自动取消协程