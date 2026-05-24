package com.kedit.routes

fun extractJsonValue(
    body: String,
    key: String
): String {

    return body
        .substringAfter("\"$key\"")
        .substringAfter(":")
        .substringAfter("\"")
        .substringBefore("\"")
}

fun extractJsonInt(
    body: String,
    key: String
): Int {

    return body
        .substringAfter("\"$key\"")
        .substringAfter(":")
        .substringBefore(",")
        .substringBefore("}")
        .trim()
        .toInt()
}