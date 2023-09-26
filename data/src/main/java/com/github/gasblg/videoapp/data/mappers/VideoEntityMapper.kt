package com.github.gasblg.videoapp.data.mappers

import com.github.gasblg.videoapp.data.database.entities.VideoEntity
import com.github.gasblg.videoapp.data.mappers.base.Mapper
import com.github.gasblg.videoapp.domain.models.Video

abstract class VideoEntityMapper : Mapper<VideoEntity, Video>