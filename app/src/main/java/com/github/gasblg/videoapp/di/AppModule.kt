package com.github.gasblg.videoapp.di

import com.github.gasblg.videoapp.data.di.DataModule
import com.github.gasblg.videoapp.data.di.RetrofitModule
import com.github.gasblg.videoapp.data.di.ManagersModule
import com.github.gasblg.videoapp.presentation.di.ViewModelModule
import dagger.Module


@Module(
    includes = [
        RetrofitModule::class,
        DataModule::class,
        ManagersModule::class,
        ViewModelModule::class
    ]
)
abstract class AppModule