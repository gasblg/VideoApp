package com.github.gasblg.videoapp.domain.usecase

import com.github.gasblg.videoapp.domain.models.Video
import com.github.gasblg.videoapp.domain.repositories.VideoRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


class VideoUseCaseTest {

    private val videoId = "o6OQh1_PFsw"

    private val video = Video(
        tag = "SsrTmKrq80F5qa-zV-prSDkAWuI",
        videoId = "o6OQh1_PFsw",
        title = "Now in Android: 75 - Android Studio Electric Eel, Architecture, Kotlin Multiplatform, and more!",
        description = "Welcome to Now in Android, your ongoing guide to what's new and notable in the world of Android development. Today, we're ...",
        date = "2023-01-18T22:00:16Z",
        imageUrl = "https://i.ytimg.com/vi/o6OQh1_PFsw/hqdefault.jpg"
    )

    private val repository = mock<VideoRepository> {
        onBlocking { getVideoById(videoId) } doReturn (video)
    }


    @Test
   fun `return correct data from repository`() = runBlocking {
        val useCase = VideoUseCase(repository = repository)
        val actual = useCase.invoke(videoId)
        val expected = Video(
            tag = "SsrTmKrq80F5qa-zV-prSDkAWuI",
            videoId = "o6OQh1_PFsw",
            title = "Now in Android: 75 - Android Studio Electric Eel, Architecture, Kotlin Multiplatform, and more!",
            description = "Welcome to Now in Android, your ongoing guide to what's new and notable in the world of Android development. Today, we're ...",
            date = "2023-01-18T22:00:16Z",
            imageUrl = "https://i.ytimg.com/vi/o6OQh1_PFsw/hqdefault.jpg"
        )
        Assert.assertEquals(expected, actual)
    }
}