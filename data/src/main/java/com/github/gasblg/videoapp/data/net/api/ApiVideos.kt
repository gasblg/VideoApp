package com.github.gasblg.videoapp.data.net.api

import com.github.gasblg.videoapp.data.net.responses.Response
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

