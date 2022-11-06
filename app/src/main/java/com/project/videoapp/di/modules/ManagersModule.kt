package com.project.videoapp.di.modules

import com.project.videoapp.core.DateManager
import com.project.videoapp.core.DateManagerImpl
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