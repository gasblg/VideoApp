package com.github.gasblg.videoapp.data.di

import com.github.gasblg.videoapp.data.date.DateManager
import com.github.gasblg.videoapp.data.date.DateManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ManagersModule {

    @Singleton
    @Provides
    fun provideDateManager(): DateManager {
        return DateManagerImpl()
    }

}