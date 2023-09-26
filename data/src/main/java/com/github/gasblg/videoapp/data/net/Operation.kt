package com.github.gasblg.videoapp.data.net

import kotlinx.coroutines.*

class Operation<T>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val retry: Retry = NetworkRetry(3),
    private val call: suspend () -> T
) {

    suspend fun run(): Result<T> = withContext(dispatcher) {
        try {
            val data = send(call, retry)
            Result.Data(data)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private suspend fun <T> send(
        block: suspend () -> T,
        retry: Retry
    ): T {
        repeat(retry.count - 1) {
            try {
                return block()
            } catch (e: Exception) {
                if (!retry.condition(e)) {
                    throw e
                }
            }
            delay(retry.timeout(it))
        }
        return block() // last attempt
    }

    companion object {
        suspend fun <T> of(
            dispatcher: CoroutineDispatcher = Dispatchers.IO,
            retry: Retry = NetworkRetry(3),
            call: suspend () -> T
        ) = Operation(dispatcher, retry, call).run()
    }

}