package com.github.gasblg.videoapp.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.github.gasblg.videoapp.data.database.entities.VideoEntity

@Dao
interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultipleVideos(videosList: List<VideoEntity>)

    @Query("SELECT * FROM videos ORDER BY date DESC")
    fun getAllVideos(): PagingSource<Int, VideoEntity>

    @Query("DELETE from videos")
    fun deleteAll()

    @Query("SELECT * FROM videos WHERE videoId = :videoId")
    suspend fun getVideoById(videoId: String): VideoEntity
}