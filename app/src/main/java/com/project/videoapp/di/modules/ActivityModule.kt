package com.project.videoapp.di.modules

import com.project.videoapp.di.scopes.ActivityScope
import com.project.videoapp.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentsBuilder::class])
    internal abstract fun contributeMainActivity(): MainActivity

}