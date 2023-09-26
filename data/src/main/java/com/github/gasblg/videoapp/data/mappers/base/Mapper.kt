package com.github.gasblg.videoapp.data.mappers.base

interface Mapper<From, To> {
    fun mapFrom(item: From): To
}