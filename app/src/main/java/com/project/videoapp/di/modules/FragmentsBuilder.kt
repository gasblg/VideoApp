package com.project.videoapp.di.modules

import com.project.videoapp.ui.fragments.video.VideoFragment
import com.project.videoapp.ui.fragments.videos.VideosFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsBuilder {

    @ContributesAndroidInjector
    abstract fun contributeVideosFragment(): VideosFragment

    @ContributesAndroidInjector
    abstract fun contributeVideoFragment(): VideoFragment
}