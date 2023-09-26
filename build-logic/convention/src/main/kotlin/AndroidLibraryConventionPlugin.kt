import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.github.gasblg.videoapp.ext.configureKotlinAndroid
import com.github.gasblg.videoapp.ext.disableUnnecessaryAndroidTests
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-kapt")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 33
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                disableUnnecessaryAndroidTests(target)
            }
            dependencies {

                //android
                add("implementation", "androidx.core:core-ktx:1.9.0")
                add("implementation", "androidx.appcompat:appcompat:1.6.1")
                add("implementation", "com.google.android.material:material:1.9.0")

                //kotlin
                add("implementation", "org.jetbrains.kotlin:kotlin-reflect:1.9.0")
                add("implementation", "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

                //dagger
                add("implementation", "com.google.dagger:dagger:2.48")
                add("implementation", "com.google.dagger:dagger-android-support:2.48")
                add("kapt", "com.google.dagger:dagger-compiler:2.48")
                add("kapt", "com.google.dagger:dagger-android-processor:2.48")
            }
        }
    }
}
