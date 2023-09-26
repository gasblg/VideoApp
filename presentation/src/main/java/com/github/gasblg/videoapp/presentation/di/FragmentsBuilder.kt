package com.github.gasblg.videoapp.presentation.di

import com.github.gasblg.videoapp.presentation.ui.fragments.video.VideoFragment
import com.github.gasblg.videoapp.presentation.ui.fragments.videos.VideosFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsBuilder {

    @ContributesAndroidInjector
    abstract fun contributeVideosFragment(): VideosFragment

    @ContributesAndroidInjector
    abstract fun contributeVideoFragment(): VideoFragment
}