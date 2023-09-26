package com.github.gasblg.videoapp.data.net.api

import com.github.gasblg.videoapp.data.enqueue
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.junit.After
import retrofit2.Retrofit
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test

class ApiVideosTest {

    private var server = MockWebServer()

    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(server.url("/"))
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val api = retrofit.create(ApiVideos::class.java)

    @After
    fun setUp() {
        server = MockWebServer()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `return correct data from list of videos`() = runBlocking {

        server.enqueue("responses/videos.json") {
            it.setResponseCode(200)
        }

        val response = api.getVideos(
            apiKey = "apiKey",
            part = "part",
            channelId = "channelId",
            maxResult = "maxResult",
            order = "order"
        )

        val video = response.items.first()

        Assert.assertEquals("cBsEs1F65kodYTF3ys3MhW3lJ08", video.etag)
        Assert.assertEquals("rdtBudmc03M", video.id?.videoId)
        Assert.assertEquals("2023-08-09T21:00:29Z", video.snippet.publishTime)
        Assert.assertEquals("https://i.ytimg.com/vi/rdtBudmc03M/hqdefault.jpg", video.snippet.thumbnails.high.url)
    }
}