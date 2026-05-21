package com.kedit.repository

import com.kedit.model.Settings

expect class SettingsRepository() {

    fun saveSettings(settings: Settings)

    fun loadSettings(): Settings
}