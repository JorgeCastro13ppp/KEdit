package com.kedit.model

data class SearchMatch(
    val index: Int,
    val line: Int,
    val column: Int,
    val lineText: String
)