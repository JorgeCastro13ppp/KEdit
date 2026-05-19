package com.kedit.model

data class SearchState(
    val isVisible: Boolean = false,
    val query: String = "",
    val replacement: String = "",
    val matches: List<SearchMatch> = emptyList(),
    val currentIndex: Int = 0,
    val caseSensitive: Boolean = false
)