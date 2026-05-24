package com.kedit

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.kedit.ui.DesktopEditorScreen
import com.kedit.ui.theme.KEditTheme

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KEdit",
    ) {
        KEditTheme(
            darkTheme = true
        ) {
            DesktopEditorScreen()
        }
    }
}