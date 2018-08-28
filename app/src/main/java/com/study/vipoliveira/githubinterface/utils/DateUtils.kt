package com.study.vipoliveira.githubinterface.utils

import java.text.SimpleDateFormat

object DateUtils {

    fun toSimpleString(timestamp: String) : String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val format = SimpleDateFormat("MM/dd/yyyy")
        return format.format(dateFormat.parse(timestamp))
    }
}