package com.kedit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.kedit.model.TerminalState
import androidx.compose.ui.input.key.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.Alignment

@Composable
fun TerminalPanel(
    state: TerminalState,
    currentDirectory: String?,
    onInputChange: (String) -> Unit,
    onExecute: () -> Unit,
    onIncreaseHeight: () -> Unit,
    onDecreaseHeight: () -> Unit
) {

    val terminalBackground =
        MaterialTheme.colorScheme.surface

    val terminalTextColor =
        MaterialTheme.colorScheme.onSurface

    Column(

        modifier = Modifier
            .fillMaxWidth()

            .height(state.height.dp)

            .border(
                width = 1.dp,
                color =
                    MaterialTheme.colorScheme.outline
            )

            .background(
                terminalBackground
            )

            .padding(12.dp)
    ) {

        // Header

        Row {

            Text(
                text = "Terminal",

                style =
                    MaterialTheme.typography.titleMedium,

                color =
                    terminalTextColor
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "＋",
                color = terminalTextColor,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable {
                        onIncreaseHeight()
                    }
            )

            Text(
                text = "－",
                color = terminalTextColor,
                modifier = Modifier
                    .clickable {
                        onDecreaseHeight()
                    }
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // History

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            items(state.history) {

                Text(

                    text = it,

                    fontFamily =
                        FontFamily.Monospace,

                    color =
                        terminalTextColor
                )
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // Input

        Row(

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Text(
                text = "${currentDirectory ?: "KEdit"}> ",

                fontFamily =
                    FontFamily.Monospace,

                color =
                    terminalTextColor
            )

            BasicTextField(

                value = state.currentInput,

                onValueChange = {
                    onInputChange(it)
                },

                textStyle = TextStyle(

                    fontFamily =
                        FontFamily.Monospace,

                    color =
                        terminalTextColor
                ),

                cursorBrush = SolidColor(
                    terminalTextColor
                ),

                modifier = Modifier

                    .fillMaxWidth()

                    .onPreviewKeyEvent {

                        if (
                            it.key == Key.Enter &&
                            it.type ==
                            KeyEventType.KeyDown
                        ) {

                            onExecute()

                            true

                        } else {

                            false
                        }
                    }
            )
        }
    }
}