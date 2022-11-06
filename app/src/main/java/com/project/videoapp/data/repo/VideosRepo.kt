package com.project.videoapp.data.repo

import androidx.paging.PagingData
import com.project.videoapp.net.responses.Item
import kotlinx.coroutines.flow.Flow

interface VideosRepo {

    suspend fun getVideos(
        part: String,
        channelId: String,
        maxResult: String,
        apiKey: String,
        date: String
    ): Flow<PagingData<Item>>

}