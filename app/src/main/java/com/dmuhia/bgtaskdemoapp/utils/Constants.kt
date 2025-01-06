package com.dmuhia.bgtaskdemoapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val ONE_TIME_WORK_REQUEST = "ONE_TIME_WORK_REQUEST"
const val PERIODIC_WORK_REQUEST = "PERIODIC_WORK_REQUEST"
const val QUOTE_TAG = "QUOTE-TAG"
fun formatTimestampToDMY(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM, yyyy | h:mm a", Locale.getDefault())
    val date = Date(timestamp)
    return sdf.format(date)
}

class Constants {
    companion object {
        const val KEY_IMAGE_URI = ""

    }

}