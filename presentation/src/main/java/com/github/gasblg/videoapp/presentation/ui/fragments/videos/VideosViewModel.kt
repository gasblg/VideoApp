package com.github.gasblg.videoapp.presentation.ui.fragments.videos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.gasblg.videoapp.domain.models.Params
import com.github.gasblg.videoapp.domain.models.Video
import com.github.gasblg.videoapp.domain.usecase.VideosUseCase

import kotlinx.coroutines.flow.*
import javax.inject.Inject


class VideosViewModel @Inject constructor(
    private val videosUseCase: VideosUseCase
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


    private fun loadVideos() = videosUseCase.invoke(
        Params(
            part = PART,
            channelId = CHANNEL_ID,
            maxResult = MAX_RESULT,
            order = ORDER
        )
    ).cachedIn(viewModelScope)


}

