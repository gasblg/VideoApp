package com.project.videoapp.data.repo

import androidx.paging.PagingData
import com.project.videoapp.data.database.entities.Video
import com.project.videoapp.data.models.InfoModel
import kotlinx.coroutines.flow.Flow

interface VideosRepo {

    fun getVideos(apiKey: String, infoModel: InfoModel): Flow<PagingData<Video>>

}