package com.kedit.repository

import com.kedit.model.Settings

actual class SettingsRepository actual constructor() {

    actual fun saveSettings(settings: Settings) {
        // Pendiente de implementación en esta plataforma
    }

    actual fun loadSettings(): Settings {
        return Settings()
    }
}