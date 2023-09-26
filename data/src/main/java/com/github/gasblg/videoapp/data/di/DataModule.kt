package com.github.gasblg.videoapp.data.di

import android.app.Application
import com.github.gasblg.videoapp.data.database.db.AppDataBase
import com.github.gasblg.videoapp.data.date.DateManager
import com.github.gasblg.videoapp.data.mappers.VideoEntityMapper
import com.github.gasblg.videoapp.data.mappers.VideoEntityMapperImpl
import com.github.gasblg.videoapp.data.mappers.VideoResponseMapper
import com.github.gasblg.videoapp.data.mappers.VideoResponseMapperImpl
import com.github.gasblg.videoapp.data.repo.VideosRepositoryImpl
import com.github.gasblg.videoapp.data.net.api.ApiVideos
import com.github.gasblg.videoapp.data.repo.VideoRepositoryImpl
import com.github.gasblg.videoapp.domain.repositories.VideoRepository
import com.github.gasblg.videoapp.domain.repositories.VideosRepository

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class DataModule {


    @Provides
    fun provideVideosRepository(
        dataBase: AppDataBase,
        retrofit: Retrofit,
        videoMapper: VideoResponseMapper,
        videoEntityMapper: VideoEntityMapper
    ): VideosRepository {
        return VideosRepositoryImpl(
            dataBase,
            retrofit.create(ApiVideos::class.java),
            videoMapper,
            videoEntityMapper
        )
    }

    @Provides
    fun provideVideoRepository(
        dataBase: AppDataBase,
        videoEntityMapper: VideoEntityMapper
    ): VideoRepository {
        return VideoRepositoryImpl(
            dataBase,
            videoEntityMapper
        )
    }

    @Provides
    fun provideResponseMapper(): VideoResponseMapper {
        return VideoResponseMapperImpl()
    }

    @Provides
    fun provideEntityMapper(dateManager: DateManager): VideoEntityMapper {
        return VideoEntityMapperImpl(dateManager)
    }

    @Provides
    @Singleton
    fun providesDB(app: Application): AppDataBase {
        return AppDataBase.invoke(app.applicationContext)
    }


}