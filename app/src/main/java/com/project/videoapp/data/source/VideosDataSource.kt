package com.project.videoapp.data.source

import androidx.paging.PagingData
import com.project.videoapp.net.responses.Item
import kotlinx.coroutines.flow.Flow

interface VideosDataSource {

    suspend fun getVideos(
        part: String,
        channelId: String,
        maxResult: String,
        apiKey: String,
        date: String
    ): Flow<PagingData<Item>>

}