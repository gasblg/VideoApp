package com.github.gasblg.videoapp.domain.repositories

import com.github.gasblg.videoapp.domain.models.Video


interface VideoRepository {

    suspend fun getVideoById(videoId: String): Video

}