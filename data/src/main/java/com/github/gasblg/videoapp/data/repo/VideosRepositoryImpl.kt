package com.github.gasblg.videoapp.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.github.gasblg.videoapp.data.database.db.AppDataBase
import com.github.gasblg.videoapp.data.mappers.VideoEntityMapper
import com.github.gasblg.videoapp.data.mappers.VideoEntityMapperImpl
import com.github.gasblg.videoapp.data.mappers.VideoResponseMapper
import com.github.gasblg.videoapp.data.mappers.VideoResponseMapperImpl
import com.github.gasblg.videoapp.domain.models.Params
import com.github.gasblg.videoapp.data.source.RemoteVideosMediator
import com.github.gasblg.videoapp.data.net.api.ApiVideos
import com.github.gasblg.videoapp.domain.repositories.VideosRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VideosRepositoryImpl @Inject constructor(
    private val database: AppDataBase,
    private val api: ApiVideos,
    private val videoResponseMapper: VideoResponseMapper,
    private val videoEntityMapper: VideoEntityMapper

) : VideosRepository {

    companion object {
        const val PAGE_SIZE = 10
        const val LABEL = "videos_list"
    }

    override fun getVideos(params: Params) = pager(params).map { pagingData ->
        pagingData.map {
            videoEntityMapper.mapFrom(it)
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun pager(
        params: Params
    ) = Pager(
        config = PagingConfig(
            PAGE_SIZE,
            enablePlaceholders = true
        ),
        remoteMediator = RemoteVideosMediator(
            LABEL,
            database,
            api,
            params,
            videoResponseMapper
        )
    ) {
        database.videosDao.getAllVideos()
    }.flow

}
