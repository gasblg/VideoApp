package com.project.videoapp.ui.fragments.videos

import androidx.lifecycle.ViewModel
import com.project.videoapp.data.repo.VideosRepo
import com.project.videoapp.net.DeveloperData.DEVELOPER_KEY
import javax.inject.Inject


class VideosViewModel @Inject constructor(
    private val videosRepo: VideosRepo
) : ViewModel() {

    companion object {
        const val CHANNEL_ID = "UCVHFbqXqoYvEWM1Ddxl0QDg"
        const val PART = "snippet"
        const val MAX_RESULT = "50"
        const val DATE = "date"
     }

    suspend fun load() = videosRepo.getVideos(
        part = PART,
        channelId = CHANNEL_ID,
        maxResult = MAX_RESULT,
        apiKey = DEVELOPER_KEY,
        date = DATE
    )

}

