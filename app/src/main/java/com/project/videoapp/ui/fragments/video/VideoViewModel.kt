package com.project.videoapp.ui.fragments.video

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.videoapp.data.database.db.AppDataBase
import com.project.videoapp.data.database.entities.Video
import com.project.videoapp.di.modules.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject


class VideoViewModel @Inject constructor(
    private val dataBase: AppDataBase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val video = MutableLiveData<Video>()

    fun getVideoInfo(videoId: String) {
        viewModelScope.launch(ioDispatcher) {
            video.postValue(dataBase.videosDao.getVideoById(videoId))
        }
    }

}

