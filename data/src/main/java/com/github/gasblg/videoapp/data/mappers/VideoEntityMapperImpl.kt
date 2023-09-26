package com.github.gasblg.videoapp.data.mappers

import com.github.gasblg.videoapp.data.database.entities.VideoEntity
import com.github.gasblg.videoapp.data.date.DateManager
import com.github.gasblg.videoapp.domain.models.Video

class VideoEntityMapperImpl(
    private val dateManager: DateManager
) : VideoEntityMapper() {
    override fun mapFrom(item: VideoEntity) = Video(
        tag = item.tag,
        videoId = item.videoId,
        title = item.title,
        description =item. description,
        date = dateManager.format(item.date),
        imageUrl = item.imageUrl
    )
}
