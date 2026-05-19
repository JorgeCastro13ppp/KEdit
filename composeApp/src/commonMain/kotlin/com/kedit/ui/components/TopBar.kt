package com.kedit.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@Composable
fun TopBar(
    documentName: String,
    onNewDocument: () -> Unit,
    onToggleTheme: () -> Unit,
    onSaveDocument: () -> Unit,
    onOpenDocument: () -> Unit,
    onToggleTerminal: () -> Unit,
    isTerminalVisible: Boolean,
    onSaveAsDocument: () -> Unit,
    onToggleSearch: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = documentName,
            style = MaterialTheme.typography.titleMedium
        )

        Button(onClick = onNewDocument) {
            Text("Nuevo")
        }
        Button(onClick = onToggleTheme) {
            Text("Tema")
        }
        Button(
            onClick = onSaveDocument
        ) {

            Text("Guardar")
        }

        Button(
            onClick = onOpenDocument
        ) {

            Text("Abrir")
        }

        Button(
            onClick = onToggleTerminal
        ) {
            Text(
                if (isTerminalVisible)
                    "Ocultar terminal"
                else
                    "Mostrar terminal"
            )
        }

        Button(
            onClick = onSaveAsDocument
        ) {
            Text("Guardar como")
        }

        Button(
            onClick = onToggleSearch
        ) {
            Text("Buscar")
        }
    }
}