package com.github.gasblg.videoapp.data.date

import java.text.SimpleDateFormat
import java.util.*

class DateManagerImpl : DateManager {

    private var serverFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
    private val dateFormatRU = SimpleDateFormat("dd MMMM yyyy", Locale.US)

    override fun format(originalDate: String): String {
         val date = try {
            serverFormat.parse(originalDate)
        } catch (e: Exception) {
            Date()
        }
        return dateFormatRU.format(date)
    }

}