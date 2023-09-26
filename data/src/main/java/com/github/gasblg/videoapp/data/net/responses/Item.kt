package com.github.gasblg.videoapp.data.net.responses


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName("etag")
    val etag: String,
    @SerialName("id")
    val id: Id? = null,
    @SerialName("kind")
    val kind: String,
    @SerialName("snippet")
    val snippet: Snippet
)