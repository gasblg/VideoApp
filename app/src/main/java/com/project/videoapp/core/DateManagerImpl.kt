package com.project.videoapp.core

import java.text.SimpleDateFormat
import java.util.*

class DateManagerImpl : DateManager {

    private var serverFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale("en"))
    private val dateFormatRU = SimpleDateFormat("HH:mm dd MMMM yyyy", Locale("ru"))

    override fun getRuDateFormat(originalDate: String): String {
         val date = try {
            serverFormat.parse(originalDate)
        } catch (e: Exception) {
            Date()
        }
        return dateFormatRU.format(date)
    }

}