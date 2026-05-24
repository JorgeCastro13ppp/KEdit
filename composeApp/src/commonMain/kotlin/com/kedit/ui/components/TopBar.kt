package com.kedit.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.unit.sp

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
    onToggleSearch: () -> Unit,
    onOpenDirectory: () -> Unit,
    onToggleExplorer: () -> Unit,
    isExplorerVisible: Boolean,
    onAccountClick: (() -> Unit)? = null,
    onSaveRemoteClick: (() -> Unit)? = null,
    onRemoteDocumentsClick: (() -> Unit)? = null
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

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState()),

            horizontalArrangement =
                Arrangement.spacedBy(4.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            TopBarAction("Nuevo", onNewDocument)
            TopBarAction("Abrir", onOpenDocument)
            TopBarAction("Guardar", onSaveDocument)
            TopBarAction("Guardar como", onSaveAsDocument)
            TopBarAction("Buscar", onToggleSearch)
            TopBarAction("Tema", onToggleTheme)

            TopBarAction(
                if (isTerminalVisible) "Terminal-" else "Terminal+",
                onToggleTerminal
            )

            TopBarAction("Carpeta", onOpenDirectory)

            TopBarAction(
                if (isExplorerVisible) "Explorador-" else "Explorador+",
                onToggleExplorer
            )
            if (onAccountClick != null) {
                TopBarAction(
                    text = "Cuenta",
                    onClick = onAccountClick
                )
            }

            if (onSaveRemoteClick != null) {
                TopBarAction(
                    text = "Guardar nube",
                    onClick = onSaveRemoteClick
                )
            }

            if (onRemoteDocumentsClick != null) {
                TopBarAction(
                    text = "Remotos",
                    onClick = onRemoteDocumentsClick
                )
            }
        }
    }


}

@Composable
private fun TopBarAction(
    text: String,
    onClick: () -> Unit
) {
    val interactionSource =
        remember {
            MutableInteractionSource()
        }

    val isHovered by
    interactionSource.collectIsHoveredAsState()

    Box(
        modifier = Modifier
            .height(32.dp)
            .background(
                if (isHovered)
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.16f)
                else
                    Color.Transparent
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            }
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp
            )
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 13.sp
        )
    }
}