package com.project.videoapp.ui.fragments.videos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.project.videoapp.data.database.entities.Video
import com.project.videoapp.data.models.InfoModel
import com.project.videoapp.data.repo.VideosRepo
import com.project.videoapp.net.DeveloperData.DEVELOPER_KEY
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class VideosViewModel @Inject constructor(
    private val videosRepo: VideosRepo
) : ViewModel() {

    companion object {
        const val CHANNEL_ID = "UCVHFbqXqoYvEWM1Ddxl0QDg"
        const val PART = "snippet"
        const val MAX_RESULT = "50"
        const val ORDER = "date"
    }

    val videoData: Flow<PagingData<Video>>

    init {
        videoData = loadVideos()
    }


    private fun loadVideos() = videosRepo.getVideos(
        apiKey = DEVELOPER_KEY,
        InfoModel(
            part = PART,
            channelId = CHANNEL_ID,
            maxResult = MAX_RESULT,
            order = ORDER
        )
    ).cachedIn(viewModelScope)


}

