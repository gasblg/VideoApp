package com.github.gasblg.videoapp.data.net.responses


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Id(
    @SerialName("kind")
    val kind: String? = null,
    @SerialName("videoId")
    val videoId: String? = null
)