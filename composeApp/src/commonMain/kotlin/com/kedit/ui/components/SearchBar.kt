package com.kedit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kedit.model.SearchState

@Composable
fun SearchBar(
    state: SearchState,
    onQueryChange: (String) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onToggleCaseSensitive: () -> Unit,
    onClear: () -> Unit,
    onClose: () -> Unit,
    onReplaceCurrent: () -> Unit,
    onReplaceAll: () -> Unit,
    onReplaceTextChange: (String) -> Unit,
) {

    val currentMatch =
        if (state.matches.isNotEmpty())
            state.matches[state.currentIndex]
        else
            null

    val resultText =
        if (state.query.isBlank()) {

            "Sin búsqueda"

        } else if (state.matches.isEmpty()) {

            "0 resultados"

        } else {

            "${state.currentIndex + 1} / ${state.matches.size} · Línea ${currentMatch?.line}, Columna ${currentMatch?.column}"
        }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(8.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Text(
                text = "Buscar:",
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            BasicTextField(
                value = state.query,

                onValueChange = {
                    onQueryChange(it)
                },

                singleLine = true,

                textStyle = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),

                cursorBrush =
                    SolidColor(
                        MaterialTheme.colorScheme.onSurfaceVariant
                    ),

                modifier = Modifier
                    .weight(1f)
                    .background(
                        MaterialTheme.colorScheme.surface
                    )
                    .padding(
                        horizontal = 8.dp,
                        vertical = 5.dp
                    )
            )

            Text(
                text = "Reemplazar:",
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            BasicTextField(
                value = state.replaceText,

                onValueChange = {
                    onReplaceTextChange(it)
                },

                singleLine = true,

                textStyle = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),

                cursorBrush =
                    SolidColor(
                        MaterialTheme.colorScheme.onSurfaceVariant
                    ),

                modifier = Modifier
                    .weight(1f)
                    .background(
                        MaterialTheme.colorScheme.surface
                    )
                    .padding(
                        horizontal = 8.dp,
                        vertical = 5.dp
                    )
            )

            Text(
                text = resultText,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            SearchAction(
                text =
                    if (state.caseSensitive)
                        "Aa"
                    else
                        "aa",
                onClick = onToggleCaseSensitive
            )

            SearchAction(
                text = "<",
                onClick = onPrevious
            )

            SearchAction(
                text = ">",
                onClick = onNext
            )

            SearchAction(
                text = "Limpiar",
                onClick = onClear
            )

            SearchAction(
                text = "Reemplazar",
                onClick = onReplaceCurrent
            )

            SearchAction(
                text = "Reemplazar todos",
                onClick = onReplaceAll
            )

            SearchAction(
                text = "X",
                onClick = onClose
            )
        }

        if (currentMatch != null) {

            Text(
                text = "Línea ${currentMatch.line}: ${currentMatch.lineText}",
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = 0.65f
                    ),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun SearchAction(
    text: String,
    onClick: () -> Unit
) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontFamily = FontFamily.Monospace,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 8.dp,
                vertical = 6.dp
            )
    )
}