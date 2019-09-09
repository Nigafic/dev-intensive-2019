package com.example.devintensive.extensions



fun String.truncate(count: Int = 16): String {
    val input = trim()
    return when {
        input.length < count -> input
        else -> "${input.substring(0, if (input[count - 1] == ' ') count - 1 else count)}..."
    }
}

fun String.stripHtml(): String = replace( "<.*?>|&amp;|&gt;|&lt;|&quot;|&apos;|&nbsp;".toRegex(), "")
    .replace("\\s+".toRegex(), " ")

