package com.github.gasblg.videoapp.domain.di

import com.github.gasblg.videoapp.domain.repositories.VideoRepository
import com.github.gasblg.videoapp.domain.repositories.VideosRepository
import com.github.gasblg.videoapp.domain.usecase.VideoUseCase
import com.github.gasblg.videoapp.domain.usecase.VideosUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideVideosUseCase(repository: VideosRepository): VideosUseCase {
        return VideosUseCase(repository)
    }

    @Provides
    fun provideVideoUseCase(repository: VideoRepository): VideoUseCase {
        return VideoUseCase(repository)
    }

}