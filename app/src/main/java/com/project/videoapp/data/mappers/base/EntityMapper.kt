package com.project.videoapp.data.mappers.base

interface EntityMapper<From, To> {
    fun mapFrom(item: From): To
}