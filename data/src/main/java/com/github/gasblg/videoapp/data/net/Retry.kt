package com.github.gasblg.videoapp.data.net

import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class Retry(
    val condition: (Exception) -> Boolean,
    val count: Int = 3,
    val timeout: (Int) -> Long = { it.toLong() }
)

class NetworkRetry(
    count: Int
) : Retry(
    condition = { it is UnknownHostException || it is SocketTimeoutException },
    count
)
