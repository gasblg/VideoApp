package com.github.gasblg.videoapp.data.mappers

import com.github.gasblg.videoapp.data.database.entities.VideoEntity
import com.github.gasblg.videoapp.data.mappers.base.Mapper
import com.github.gasblg.videoapp.data.net.responses.Item

abstract class VideoResponseMapper : Mapper<Item, VideoEntity>