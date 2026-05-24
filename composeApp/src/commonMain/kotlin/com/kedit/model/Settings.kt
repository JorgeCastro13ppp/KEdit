package com.kedit.model

data class Settings(
    var isDarkMode: Boolean = true,
    var autoSaveEnabled: Boolean = true,
    var lastDirectory: String? = null
)