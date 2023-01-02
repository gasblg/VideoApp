package com.project.videoapp.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.project.videoapp.data.database.db.AppDataBase
import com.project.videoapp.data.mappers.VideoMapper
import com.project.videoapp.data.models.InfoModel
import com.project.videoapp.data.source.RemoteVideosMediator
import com.project.videoapp.net.api.ApiVideos
import kotlinx.coroutines.*
import javax.inject.Inject

class VideosRepoImpl @Inject constructor(
    private val database: AppDataBase,
    private val api: ApiVideos,
    private val videoMapper: VideoMapper
) : VideosRepo {

    companion object {
        const val PAGE_SIZE = 10
        const val LABEL = "videos_list"
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getVideos(
        apiKey: String,
        infoModel: InfoModel
    ) = Pager(
        config = PagingConfig(
            PAGE_SIZE,
            enablePlaceholders = true
        ),
        remoteMediator = RemoteVideosMediator(
            LABEL,
            database,
            api,
            apiKey,
            infoModel,
            videoMapper
        )
    ) {
        database.videosDao.getAllVideos()
    }.flow

}
