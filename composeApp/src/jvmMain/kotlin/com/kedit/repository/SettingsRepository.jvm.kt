package com.kedit.repository

import com.kedit.model.Settings
import java.io.File
import java.util.Properties

actual class SettingsRepository actual constructor() {

    private val settingsFile =
        File(
            System.getProperty("user.home"),
            ".kedit/settings.properties"
        )

    actual fun saveSettings(settings: Settings) {

        settingsFile.parentFile.mkdirs()

        val properties =
            Properties()

        properties.setProperty(
            "isDarkMode",
            settings.isDarkMode.toString()
        )

        properties.setProperty(
            "autoSaveEnabled",
            settings.autoSaveEnabled.toString()
        )

        settingsFile.outputStream().use { output ->
            properties.store(
                output,
                "KEdit settings"
            )
        }
    }

    actual fun loadSettings(): Settings {

        if (!settingsFile.exists())
            return Settings()

        val properties =
            Properties()

        settingsFile.inputStream().use { input ->
            properties.load(input)
        }

        return Settings(
            isDarkMode =
                properties.getProperty(
                    "isDarkMode",
                    "true"
                ).toBoolean(),

            autoSaveEnabled =
                properties.getProperty(
                    "autoSaveEnabled",
                    "true"
                ).toBoolean()
        )
    }
}