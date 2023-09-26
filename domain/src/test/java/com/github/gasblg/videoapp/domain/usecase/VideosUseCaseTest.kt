package com.github.gasblg.videoapp.domain.usecase

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.github.gasblg.videoapp.domain.MainDispatcherRule
import com.github.gasblg.videoapp.domain.models.Params
import com.github.gasblg.videoapp.domain.models.Video
import com.github.gasblg.videoapp.domain.repositories.VideosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


class VideosUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val params = mock<Params>()

    private val video = Video(
        tag = "_Cw_OfbmX7u2k4zh1jNp5qa_oDI",
        videoId = "xc8nAcVvpxY",
        title = "Fundamentals of Compose Layouts and Modifiers - MAD Skills",
        description = "In this video of Compose Layouts and Modifiers, Simona will talk about the fundamentals - how Layouts and Modifiers work ...",
        date = "2023-01-31T00:00:35Z",
        imageUrl = "https://i.ytimg.com/vi/xc8nAcVvpxY/hqdefault.jpg"
    )

    private val listOfVideos = listOf(
        Video(
            tag = "_Cw_OfbmX7u2k4zh1jNp5qa_oDI",
            videoId = "xc8nAcVvpxY",
            title = "Fundamentals of Compose Layouts and Modifiers - MAD Skills",
            description = "In this video of Compose Layouts and Modifiers, Simona will talk about the fundamentals - how Layouts and Modifiers work ...",
            date = "2023-01-31T00:00:35Z",
            imageUrl = "https://i.ytimg.com/vi/xc8nAcVvpxY/hqdefault.jpg"
        ),
        Video(
            tag = "SsrTmKrq80F5qa-zV-prSDkAWuI",
            videoId = "o6OQh1_PFsw",
            title = "Now in Android: 75 - Android Studio Electric Eel, Architecture, Kotlin Multiplatform, and more!",
            description = "Welcome to Now in Android, your ongoing guide to what's new and notable in the world of Android development. Today, we're ...",
            date = "2023-01-18T22:00:16Z",
            imageUrl = "https://i.ytimg.com/vi/o6OQh1_PFsw/hqdefault.jpg"
        )
    )

    private val moviesPageStream = flowOf(
        PagingData.from(
            listOfVideos.map {
                it
            }
        )
    )

    private val repository = mock<VideosRepository> {
        onBlocking { getVideos(params) } doReturn (moviesPageStream)
    }


    @Test
    fun `return correct data from repository`() = runBlocking {

        val useCase = VideosUseCase(repository = repository)
        val actual = useCase.invoke(params)

        val differ = AsyncPagingDataDiffer(
            diffCallback = TestDiffCallback<Video>(),
            updateCallback = TestListCallback(),
            workerDispatcher = Dispatchers.Main
        )

        differ.submitData(actual.first())
        val list = differ.snapshot().items

        Assert.assertEquals(list.first(), video)
        Assert.assertEquals(list.size, listOfVideos.size)

    }
}


class TestListCallback : ListUpdateCallback {
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
}

class TestDiffCallback<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}