package com.kedit.model

data class FileItem(
    val name: String,
    val path: String,
    val isDirectory: Boolean = false
)