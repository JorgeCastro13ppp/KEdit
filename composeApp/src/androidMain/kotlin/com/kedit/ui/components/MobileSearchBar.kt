package com.kedit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
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
fun MobileSearchBar(
    state: SearchState,
    onQueryChange: (String) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onToggleCaseSensitive: () -> Unit,
    onClear: () -> Unit,
    onClose: () -> Unit
) {

    val resultText =
        if (state.query.isBlank()) {
            "Sin búsqueda"
        } else if (state.matches.isEmpty()) {
            "0 resultados"
        } else {
            "${state.currentIndex + 1}/${state.matches.size}"
        }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(
                horizontal = 8.dp,
                vertical = 6.dp
            )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Text(
                text = "Buscar:",
                fontSize = 11.sp,
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
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                cursorBrush =
                    SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
                modifier = Modifier
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(
                        horizontal = 6.dp,
                        vertical = 5.dp
                    )
            )

            Text(
                text = resultText,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Aa",
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onToggleCaseSensitive()
                }
            )

            Text(
                text = "<",
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onPrevious()
                }
            )

            Text(
                text = ">",
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onNext()
                }
            )

            Text(
                text = "Limpiar",
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onClear()
                }
            )

            Text(
                text = "X",
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onClose()
                }
            )
        }
    }
}