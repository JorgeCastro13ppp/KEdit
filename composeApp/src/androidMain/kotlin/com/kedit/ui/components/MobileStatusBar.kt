package com.kedit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kedit.model.Document

@Composable
fun MobileStatusBar(
    activeDocument: Document?
) {

    val documentName =
        activeDocument?.name ?: "Sin documento"

    val content =
        activeDocument?.content ?: ""

    val lineCount =
        content.lines().size.coerceAtLeast(1)

    val characterCount =
        content.length

    Text(
        text = "$documentName · Líneas: $lineCount · Caracteres: $characterCount",
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                horizontal = 10.dp,
                vertical = 7.dp
            )
            .navigationBarsPadding(),
        fontSize = 11.sp,
        fontFamily = FontFamily.Monospace,
        color = MaterialTheme.colorScheme.onSurface
    )
}