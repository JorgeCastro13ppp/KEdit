package com.kedit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WebTopBar(
    documentName: String,
    onNewDocument: () -> Unit,
    onToggleTheme: () -> Unit,
    onToggleSearch: () -> Unit,
    onShowVersions: () -> Unit,
    onToggleSidebar: () -> Unit,
    onAccountClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                horizontal = 12.dp,
                vertical = 10.dp
            ),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = documentName,
            fontSize = 14.sp,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Row(
            horizontalArrangement =
                Arrangement.spacedBy(22.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Text(
                text = "Nuevo",
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable {
                    onNewDocument()
                }
            )

            Text(
                text = "Buscar",
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable {
                    onToggleSearch()
                }
            )

            Text(
                text = "Tema",
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable {
                    onToggleTheme()
                }
            )

            Text(
                text = "Documentos",
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable {
                    onToggleSidebar()
                }
            )

            Text(
                text = "Cuenta",
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable {
                    onAccountClick()
                }
            )

            Text(
                text = "Otras versiones",
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable {
                    onShowVersions()
                }
            )
        }
    }
}