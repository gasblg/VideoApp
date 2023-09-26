package com.github.gasblg.videoapp.domain.models

import java.io.Serializable

data class Params(
    val part: String,
    val channelId: String,
    val maxResult: String,
    val order: String
) : Serializable

