plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
}

android {
    compileSdk = 33
    defaultConfig {
        applicationId = app.Config.pkg
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", app.Fields.API_VIDEOS, "\"${app.Api.link}\"")

        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    namespace = app.Config.pkg
}

dependencies {

    // tests
    testImplementation(deps.Tests.junit)
    androidTestImplementation(deps.Tests.extJunit)
    androidTestImplementation(deps.Tests.espressoCore)

    // androidx && google
    implementation(deps.AndroidX.core)
    implementation(deps.AndroidX.appcompat)
    implementation(deps.AndroidX.constraint)
    implementation(deps.AndroidX.swipe)
    implementation(deps.AndroidX.paging)
    implementation(deps.AndroidX.lifecycleExtensions)
    implementation(deps.AndroidX.lifecycleLivedata)
    implementation(deps.AndroidX.lifecycleViewModel)
    implementation(deps.Google.material)

    // dagger
    implementation(deps.Dagger.dagger)
    implementation(deps.Dagger.supportDagger)
    kapt(deps.Dagger.daggerCompiler)
    kapt(deps.Dagger.daggerAndroid)

    // retrofit
    implementation(deps.Retrofit.retrofit)
    implementation(deps.Retrofit.logging)
    implementation(deps.Retrofit.okhttp)

    // kotlin
    implementation(deps.Kotlin.std)
    implementation(deps.Kotlin.reflect)
    implementation(deps.Kotlin.X.coroutines)
    implementation(deps.Kotlin.X.coroutinesAndroid)
    implementation(deps.Kotlin.X.serializationJson)
    implementation(deps.Kotlin.X.serializationConverter)

    // glide
    implementation(deps.Glide.glide)
    kapt(deps.Glide.glideKapt)

    // navigation
    implementation(deps.AndroidX.navigationFragment)
    implementation(deps.AndroidX.navigationUi)

    // youtube
    implementation(deps.Youtube.player)
}
