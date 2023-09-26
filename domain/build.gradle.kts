plugins {
    id("videoapp.android.library")
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.github.gasblg.videoapp.domain"
}

dependencies {

    //testing
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.kotlinx.coroutines.test)

    //android
    implementation(libs.androidx.paging.runtime.ktx)

    //kotlin
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    //room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

}