package com.github.gasblg.videoapp.data.dao

import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.github.gasblg.videoapp.data.database.dao.VideoDao
import com.github.gasblg.videoapp.data.database.db.AppDataBase
import com.github.gasblg.videoapp.data.database.entities.VideoEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class VideoDaoTest {

    private lateinit var database: AppDataBase
    private lateinit var dao: VideoDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).allowMainThreadQueries().build()
        dao = database.videosDao
    }

    @After
    fun tearDown() {
        database.close()
    }


    private val listOfVideos = listOf(
        VideoEntity(
            tag = "_Cw_OfbmX7u2k4zh1jNp5qa_oDI",
            videoId = "xc8nAcVvpxY",
            title = "Fundamentals of Compose Layouts and Modifiers - MAD Skills",
            description = "In this video of Compose Layouts and Modifiers, Simona will talk about the fundamentals - how Layouts and Modifiers work ...",
            date = "2023-01-31T00:00:35Z",
            imageUrl = "https://i.ytimg.com/vi/xc8nAcVvpxY/hqdefault.jpg"
        ),
        VideoEntity(
            tag = "SsrTmKrq80F5qa-zV-prSDkAWuI",
            videoId = "o6OQh1_PFsw",
            title = "Now in Android: 75 - Android Studio Electric Eel, Architecture, Kotlin Multiplatform, and more!",
            description = "Welcome to Now in Android, your ongoing guide to what's new and notable in the world of Android development. Today, we're ...",
            date = "2023-01-18T22:00:16Z",
            imageUrl = "https://i.ytimg.com/vi/o6OQh1_PFsw/hqdefault.jpg"
        )
    )

    @Test
    fun insertMultipleVideos() = runBlocking {
        dao.insertMultipleVideos(listOfVideos)
        val expected = listOfVideos.first().tag
        val actual = getVideos().first().tag

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getAllVideos() = runBlocking {
        dao.insertMultipleVideos(listOfVideos)
        val expected = listOfVideos.size
        val actual = getVideos().size

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun deleteAllVideos() = runBlocking {
        dao.insertMultipleVideos(listOfVideos)
        dao.deleteAll()
        val actual = getVideos().size

        Assert.assertEquals(0, actual)
    }

    @Test
    fun getVideoById() = runBlocking {
        dao.insertMultipleVideos(listOfVideos)
        val expected =listOfVideos.first().videoId
        val actual = dao.getVideoById("xc8nAcVvpxY").videoId

        Assert.assertEquals(expected, actual)

    }

    private suspend fun getVideos(): List<VideoEntity> {
        val allVideos = dao.getAllVideos().load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )
        val pagingData = allVideos as PagingSource.LoadResult.Page<Int, VideoEntity>
        return pagingData.data
    }
}