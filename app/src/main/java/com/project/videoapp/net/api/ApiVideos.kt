package com.project.videoapp.net.api

import com.project.videoapp.net.responses.Response
import retrofit2.http.*

interface ApiVideos {

    @GET("search")
    suspend fun getVideos(
        @Query("key") apiKey: String,
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("maxResults") maxResult: String,
        @Query("order") order: String,
        @Query("pageToken") pageToken: String? = null
    ): Response

}

