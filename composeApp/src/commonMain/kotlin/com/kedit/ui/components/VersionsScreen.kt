package com.kedit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VersionsScreen(
    onBack: () -> Unit
) {

    val uriHandler =
        LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp)
    ) {

        Text(
            text = "Otras versiones",
            fontSize = 22.sp,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Desde esta sección se pueden descargar las versiones instalables de KEdit.",
            fontSize = 14.sp,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        VersionItem(
            title = "KEdit Desktop MSI",
            description = "Instalador principal para Windows con editor completo, terminal, explorador, guardado local y conexión con backend.",
            fileName = "kedit.msi",
            onClick = {
                uriHandler.openUri("downloads/kedit.msi")
            }
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        VersionItem(
            title = "KEdit Desktop EXE",
            description = "Ejecutable de escritorio para Windows incluido como alternativa de distribución.",
            fileName = "kedit.exe",
            onClick = {
                uriHandler.openUri("downloads/kedit.exe")
            }
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        VersionItem(
            title = "KEdit Android",
            description = "Versión móvil recortada para edición básica, pestañas, búsqueda, cambio de tema e inicio de sesión.",
            fileName = "kedit.apk",
            onClick = {
                uriHandler.openUri("downloads/kedit.apk")
            }
        )

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        Text(
            text = "Pulsa sobre el nombre del archivo para descargarlo.",
            fontSize = 13.sp,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Text(
            text = "Volver al editor",
            fontSize = 13.sp,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                onBack()
            }
        )
    }
}

@Composable
private fun VersionItem(
    title: String,
    description: String,
    fileName: String,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(18.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = description,
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Monospace,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(
                            alpha = 0.75f
                        )
                )
            }

            Spacer(
                modifier = Modifier.width(24.dp)
            )

            Text(
                text = fileName,
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onClick()
                }
            )
        }
    }
}