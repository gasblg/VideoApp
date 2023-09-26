package com.github.gasblg.videoapp.domain.usecase

import com.github.gasblg.videoapp.domain.models.Params
import com.github.gasblg.videoapp.domain.repositories.VideosRepository


class VideosUseCase(private val repository: VideosRepository) {

    fun invoke(params: Params) = repository.getVideos(params)
}