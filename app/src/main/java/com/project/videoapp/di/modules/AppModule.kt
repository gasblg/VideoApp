package com.project.videoapp.di.modules

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