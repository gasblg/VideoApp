package com.github.gasblg.videoapp.data

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import java.nio.charset.StandardCharsets

fun MockWebServer.enqueue(
    path: String,
    block: ((MockResponse) -> Unit)? = null
) {
    val fs = javaClass.classLoader.getResourceAsStream(path)
    with(fs!!.source().buffer()) {
        val mockedResponse = MockResponse()
            .setResponseCode(200)
            .setBody(this.readString(StandardCharsets.UTF_8))
            .apply { block?.invoke(this) }
        enqueue(mockedResponse)
    }
}
