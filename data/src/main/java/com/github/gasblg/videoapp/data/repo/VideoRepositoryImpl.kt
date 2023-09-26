package com.github.gasblg.videoapp.data.repo


import com.github.gasblg.videoapp.data.database.db.AppDataBase
import com.github.gasblg.videoapp.data.mappers.VideoEntityMapper
import com.github.gasblg.videoapp.domain.repositories.VideoRepository
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val database: AppDataBase,
    private val videoEntityMapper: VideoEntityMapper
) : VideoRepository {

    override suspend fun getVideoById(videoId: String) =
        videoEntityMapper.mapFrom(database.videosDao.getVideoById(videoId))

}
