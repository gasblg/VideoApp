package com.github.gasblg.videoapp.data.mappers

import com.github.gasblg.videoapp.data.database.entities.VideoEntity
import com.github.gasblg.videoapp.data.net.responses.Item

class VideoResponseMapperImpl : VideoResponseMapper() {
    override fun mapFrom(item: Item) = VideoEntity(
        tag = item.etag,
        videoId = item.id?.videoId,
        title = item.snippet.title,
        description = item.snippet.description,
        date = item.snippet.publishTime,
        imageUrl = item.snippet.thumbnails.high.url
    )
}
