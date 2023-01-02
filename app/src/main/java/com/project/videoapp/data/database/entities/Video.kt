package com.project.videoapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos")
data class Video(
    @PrimaryKey(autoGenerate = false)
    val tag: String,
    val videoId: String? = "",
    val title: String,
    val description: String,
    val date: String,
    val imageUrl: String
)