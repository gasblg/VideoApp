package com.project.videoapp.data.mappers

import com.project.videoapp.data.database.entities.Video
import com.project.videoapp.data.mappers.base.EntityMapper
import com.project.videoapp.net.responses.Item

abstract class VideoMapper : EntityMapper<Item, Video>