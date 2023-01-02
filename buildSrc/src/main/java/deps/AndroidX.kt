package deps

object AndroidX {

    private const val coreVersion = "1.9.0"
    private const val appcompatVersion = "1.5.1"
    private const val constraintVersion = "2.1.4"
    private const val swipeVersion = "1.1.0"
    private const val pagingVersion = "3.1.1"
    private const val lcExtensionsVersion = "2.2.0"
    private const val lcLivedataVersion = "2.5.1"
    private const val lcViewModelVersion = "2.5.1"
    private const val navigationVersion = "2.5.2"
    private const val roomVersion = "2.4.2"

    const val core = "androidx.core:core-ktx:$coreVersion"
    const val appcompat = "androidx.appcompat:appcompat:$appcompatVersion"
    const val constraint = "androidx.constraintlayout:constraintlayout:$constraintVersion"
    const val swipe = "androidx.swiperefreshlayout:swiperefreshlayout:$swipeVersion"
    const val paging = "androidx.paging:paging-runtime-ktx:$pagingVersion"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:$lcExtensionsVersion"
    const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:$lcLivedataVersion"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lcViewModelVersion"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:$navigationVersion"
    const val room = "androidx.room:room-runtime:$roomVersion"
    const val roomKapt = "androidx.room:room-compiler:$roomVersion"
    const val roomKtx = "androidx.room:room-ktx:$roomVersion"
    const val roomPaging = "androidx.room:room-paging:$roomVersion"
    const val lifecycleState =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lcViewModelVersion"
}