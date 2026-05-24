package com.kedit

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.kedit.ui.WebEditorScreen

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        WebEditorScreen()
    }
}