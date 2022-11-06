package com.project.videoapp.data.repo

import androidx.paging.PagingData
import com.project.videoapp.data.source.VideosDataSource
import com.project.videoapp.net.responses.Item
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VideosRepoImpl @Inject constructor(
    private val dataSource: VideosDataSource
) : VideosRepo {

    override suspend fun getVideos(
        part: String,
        channelId: String,
        maxResult: String,
        apiKey: String,
        date: String
    ): Flow<PagingData<Item>> =
        dataSource.getVideos(part, channelId, maxResult, apiKey, date)
}