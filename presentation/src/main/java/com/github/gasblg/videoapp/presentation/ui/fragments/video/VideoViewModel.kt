package com.github.gasblg.videoapp.presentation.ui.fragments.video

import androidx.lifecycle.*
import com.github.gasblg.videoapp.domain.di.IoDispatcher
import com.github.gasblg.videoapp.domain.models.Video
import com.github.gasblg.videoapp.domain.usecase.VideoUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


class VideoViewModel @Inject constructor(
    private val videoUseCase: VideoUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _video = Channel<Video>()
    val video = _video.receiveAsFlow()

    fun getVideoInfo(videoId: String) {
        viewModelScope.launch(ioDispatcher) {
            _video.send(videoUseCase.invoke(videoId))
        }
    }

}

