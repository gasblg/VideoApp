package com.project.videoapp.di.modules

import android.app.Application
import com.project.videoapp.data.database.db.AppDataBase
import com.project.videoapp.data.mappers.VideoMapper
import com.project.videoapp.data.mappers.VideoMapperImpl
import com.project.videoapp.data.repo.VideosRepo
import com.project.videoapp.data.repo.VideosRepoImpl
import com.project.videoapp.net.api.ApiVideos

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IoDispatcher

@Module
class DataModule {


    @Provides
    fun provideRepo(
        dataBase: AppDataBase,
        retrofit: Retrofit,
        videoMapper: VideoMapper,
    ): VideosRepo {
        return VideosRepoImpl(
            dataBase,
            retrofit.create(ApiVideos::class.java),
            videoMapper
        )
    }

    @Provides
    fun provideMapper(): VideoMapper {
        return VideoMapperImpl()
    }

    @Provides
    @Singleton
    fun providesDB(app: Application): AppDataBase {
        return AppDataBase.invoke(app.applicationContext)
    }

    @Provides
    @IoDispatcher
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}