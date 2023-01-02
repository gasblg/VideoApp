package com.project.videoapp.data.mappers

import com.project.videoapp.data.database.entities.Video
import com.project.videoapp.net.responses.Item

class VideoMapperImpl : VideoMapper() {
    override fun mapFrom(item: Item) = Video(
        tag = item.etag,
        videoId = item.id?.videoId,
        title = item.snippet.title,
        description = item.snippet.description,
        date = item.snippet.publishTime,
        imageUrl = item.snippet.thumbnails.high.url
    )
}
