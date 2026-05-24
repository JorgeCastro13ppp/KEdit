package com.kedit.model

import kotlin.random.Random
import kotlin.time.Clock
fun generateId(): String {
    return Random.nextInt(100000, 999999).toString()
}

data class Document(
    val id: String = generateId(),
    val remoteId: Int? = null,
    var name: String = "Untitled",
    var path: String? = null,
    var content: String = "",
    var isModified: Boolean = false,
    var lastModified: Long = Clock.System.now().toEpochMilliseconds()
)