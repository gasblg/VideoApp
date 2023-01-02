package com.project.videoapp.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.project.videoapp.data.database.entities.Video

@Dao
interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultipleVideos(videosList: List<Video>)

    @Query("SELECT * FROM videos ORDER BY date DESC")
    fun getAllVideos(): PagingSource<Int, Video>

    @Query("DELETE from videos")
    fun deleteAll()

    @Query("SELECT * FROM videos WHERE tag = :videoId")
    suspend fun getVideoById(videoId: String): Video
}