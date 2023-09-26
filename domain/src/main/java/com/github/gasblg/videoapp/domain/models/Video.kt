package com.github.gasblg.videoapp.domain.models

import java.io.Serializable

data class Video(
    val tag: String,
    val videoId: String? = "",
    val title: String,
    val description: String,
    val date: String,
    val imageUrl: String
): Serializable