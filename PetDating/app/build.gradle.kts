plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt") //Kotlin 注解处理工具
    id ("kotlin-parcelize")  // 替代弃用的 kotlin-android-extensions
}

android {
    namespace = "com.example.petdating"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.petdating"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // blankj
    implementation (libs.utilcodex)

    // 屏幕适配
    implementation (libs.androidautosize)

    // 第三方recyclerview
    implementation (libs.xx)

    // 微信开源项目，替代SP
    implementation (libs.mmkv.static)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Material Components 支持
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.legacy.support.v4)
    // jetpack
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment)

    // retrofit2
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // glide 加载图片
    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    implementation(libs.androidx.fragment.ktx)
    implementation(libs.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}