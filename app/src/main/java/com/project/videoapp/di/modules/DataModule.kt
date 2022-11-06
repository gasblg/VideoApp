package com.project.videoapp.di.modules


import com.project.videoapp.data.repo.VideosRepo
import com.project.videoapp.data.repo.VideosRepoImpl
import com.project.videoapp.data.source.VideosDataSource
import com.project.videoapp.data.source.VideosDataSourceImpl
import com.project.videoapp.net.api.ApiVideos

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class DataModule {


    @Provides
    fun provideRepo(datasource: VideosDataSource): VideosRepo {
        return VideosRepoImpl(datasource)
    }

    @Provides
    fun provideDataSource(retrofit: Retrofit): VideosDataSource {
        return VideosDataSourceImpl(retrofit.create(ApiVideos::class.java))
    }

}