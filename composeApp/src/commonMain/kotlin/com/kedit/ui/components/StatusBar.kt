package com.kedit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kedit.model.Document

@Composable
fun StatusBar(
    activeDocument: Document?,
    isDarkMode: Boolean,
    autoSaveEnabled: Boolean
) {

    val documentName =
        activeDocument?.name ?: "Sin documento"

    val content =
        activeDocument?.content ?: ""

    val characterCount =
        content.length

    val lineCount =
        content.lines().size.coerceAtLeast(1)

    val status =
        if (activeDocument?.isModified == true)
            "Modificado"
        else
            "Guardado"

    val theme =
        if (isDarkMode)
            "Oscuro"
        else
            "Claro"

    val autoSave =
        if (autoSaveEnabled)
            "ON"
        else
            "OFF"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surface
            )
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp
            ),

        horizontalArrangement =
            Arrangement.Start
    ) {

        Text(
            text = "Documento: $documentName",
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Text(
            text = "Líneas: $lineCount | Caracteres: $characterCount | $status | Tema: $theme | AutoSave: $autoSave",
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}