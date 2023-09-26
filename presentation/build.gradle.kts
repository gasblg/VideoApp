plugins {
    id("videoapp.android.library")
}

android {
    namespace = "com.github.gasblg.videoapp.presentation"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":domain"))

    //testing
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso)

    //android
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.swiperefreshlayout)

    //navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //youtube
    implementation(libs.core)

    //glide
    implementation(libs.glide)

}