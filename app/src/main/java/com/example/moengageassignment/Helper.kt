package com.example.moengageassignment

import android.view.View
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class RequestMethod{
    GET,
    POST,
    PUT,
    DELETE
}

enum class SortOptions(val sortType : String) {
    NEW_TO_OLD("New to Old"),
    OLD_TO_NEW("Old to New"),
    NONE("NONE")
}

 fun parseDate(dateString: String): Date {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    return dateFormat.parse(dateString) ?: Date(0)
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

