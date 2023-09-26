package com.github.gasblg.videoapp.domain.usecase

import com.github.gasblg.videoapp.domain.repositories.VideoRepository


class VideoUseCase(private val repository: VideoRepository) {

    suspend fun invoke(videoId: String) = repository.getVideoById(videoId)

}