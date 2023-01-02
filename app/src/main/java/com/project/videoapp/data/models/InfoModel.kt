package com.project.videoapp.data.models

import java.io.Serializable

data class InfoModel(
    val part: String,
    val channelId: String,
    val maxResult: String,
    val order: String
) : Serializable

