package com.github.gasblg.videoapp.domain.repositories

import androidx.paging.PagingData
import com.github.gasblg.videoapp.domain.models.Params
import com.github.gasblg.videoapp.domain.models.Video

import kotlinx.coroutines.flow.Flow

interface VideosRepository {

    fun getVideos(params: Params): Flow<PagingData<Video>>

}