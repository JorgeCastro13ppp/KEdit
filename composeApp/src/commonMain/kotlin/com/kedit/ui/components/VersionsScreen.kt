package com.kedit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VersionsScreen(
    onBack: () -> Unit
) {

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
            text = "Desde esta sección se podrán descargar las versiones instalables de KEdit.",
            fontSize = 14.sp,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        VersionItem(
            title = "KEdit Desktop",
            description = "Versión principal para escritorio con editor completo, terminal, explorador y guardado local.",
            fileName = "kedit-desktop.exe"
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        VersionItem(
            title = "KEdit Android",
            description = "Versión móvil recortada para edición básica, pestañas, búsqueda y cambio de tema.",
            fileName = "kedit-android.apk"
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
    fileName: String
) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(18.dp)
    ) {

        Row(
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
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}