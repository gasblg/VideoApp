package com.github.gasblg.videoapp.di

import android.app.Application
import com.github.gasblg.videoapp.App
import com.github.gasblg.videoapp.domain.di.DispatchersModule
import com.github.gasblg.videoapp.presentation.di.ActivityModule
import com.github.gasblg.videoapp.domain.di.UseCaseModule
import dagger.BindsInstance

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        UseCaseModule::class,
        DispatchersModule::class
    ]
)


interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: App)
}
