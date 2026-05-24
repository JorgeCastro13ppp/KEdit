package com.kedit.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.key.isShiftPressed

fun Modifier.keditKeyboardShortcuts(
    onNewDocument: () -> Unit,
    onSaveDocument: () -> Unit,
    onOpenDocument: () -> Unit,
    onCloseDocument: () -> Unit,
    onToggleTheme: () -> Unit,
    onToggleTerminal: () -> Unit,
    onSaveDocumentAs: () -> Unit,
    onToggleSearch: () -> Unit,
    onGoToParentDirectory: () -> Unit,
    onToggleExplorer: () -> Unit
): Modifier {

    return this.onPreviewKeyEvent { event ->

        if (
            event.type == KeyEventType.KeyDown &&
            event.isCtrlPressed
        ) {

            when (event.key) {

                Key.N -> {
                    onNewDocument()
                    true
                }

                Key.S -> {

                    if (event.isShiftPressed)
                        onSaveDocumentAs()
                    else
                        onSaveDocument()

                    true
                }

                Key.O -> {
                    onOpenDocument()
                    true
                }

                Key.W -> {
                    onCloseDocument()
                    true
                }

                Key.T -> {
                    onToggleTheme()
                    true
                }

                Key.J -> {
                    onToggleTerminal()
                    true
                }
                Key.F -> {
                    onToggleSearch()
                    true
                }

                Key.B -> {
                    onGoToParentDirectory()
                    true
                }

                Key.D -> {
                    onToggleExplorer()
                    true
                }

                else -> false
            }

        } else {

            false
        }
    }
}