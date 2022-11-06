package deps

object Retrofit {

    private const val version = "2.9.0"
    private const val okhttpVersion = "4.9.3"

    const val retrofit = "com.squareup.retrofit2:retrofit:$version"
    const val logging = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"
    const val okhttp = "com.squareup.okhttp3:okhttp:$okhttpVersion"
}