package deps

object Kotlin {

    const val version = "1.7.20"
    private const val stdVersion = "1.7.20"
    private const val reflectVersion = "1.7.20"
    private const val coroutinesVersion = "1.6.2"
    private const val serializationVersion = "1.3.1"
    private const val converterVersion = "0.8.0"

    const val std = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$stdVersion"
    const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$reflectVersion"

    object X {

        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
        const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion"
        const val serializationConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:$converterVersion"
    }

}
