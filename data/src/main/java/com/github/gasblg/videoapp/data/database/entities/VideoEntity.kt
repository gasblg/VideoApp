package com.github.gasblg.videoapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos")
data class VideoEntity(
    @PrimaryKey(autoGenerate = false)
    val tag: String,
    val videoId: String? = "",
    val title: String,
    val description: String,
    val date: String,
    val imageUrl: String
)