package com.github.gasblg.videoapp.data.net.responses


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    @SerialName("etag")
    val etag: String,
    @SerialName("items")
    val items: List<Item>,
    @SerialName("kind")
    val kind: String,
    @SerialName("nextPageToken")
    val nextPageToken: String? = null,
    @SerialName("prevPageToken")
    val prevPageToken: String? = null,
    @SerialName("pageInfo")
    val pageInfo: PageInfo,
    @SerialName("regionCode")
    val regionCode: String
)