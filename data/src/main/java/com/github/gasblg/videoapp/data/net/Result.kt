package com.github.gasblg.videoapp.data.net

sealed class Result<out T> {

    data class Data<out T>(
        val data: T
    ) : Result<T>()

    data class Error<out T>(
        val error: Throwable
    ) : Result<T>()

    fun asData(): T? = (this as? Data<T>)?.data
}