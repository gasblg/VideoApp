package com.github.gasblg.videoapp.presentation.di


import com.github.gasblg.videoapp.presentation.di.scopes.ActivityScope
import com.github.gasblg.videoapp.presentation.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentsBuilder::class])
    internal abstract fun contributeMainActivity(): MainActivity

}