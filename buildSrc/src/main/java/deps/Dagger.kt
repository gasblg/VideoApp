package deps

object Dagger {

    private const val version = "2.44"
    private const val supportVersion = "2.38.1"

    const val dagger = "com.google.dagger:dagger:$version"
    const val supportDagger = "com.google.dagger:dagger-android-support:$supportVersion"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:$version"
    const val daggerAndroid = "com.google.dagger:dagger-android-processor:$supportVersion"
}