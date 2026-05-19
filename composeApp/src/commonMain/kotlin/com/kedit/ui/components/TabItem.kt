package com.kedit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kedit.model.Document

@Composable
fun TabItem(
    document: Document,
    isActive: Boolean,
    onClick: () -> Unit,
    onClose: () -> Unit
) {

    val background =
        if (isActive)
            MaterialTheme.colorScheme.primaryContainer
        else
            MaterialTheme.colorScheme.surfaceVariant

    val textColor =
        if (isActive)
            MaterialTheme.colorScheme.onPrimaryContainer
        else
            MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        modifier = Modifier
            .background(background)
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 6.dp,
                vertical = 8.dp
            )
            .widthIn(min = 120.dp, max = 220.dp),

        horizontalArrangement =
            Arrangement.spacedBy(10.dp),

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Text(
            text =
                document.name +
                        if (document.isModified) "*" else "",

            color =
                textColor,

            fontSize =
                13.sp,

            fontFamily =
                FontFamily.Monospace,

            modifier =
                Modifier.weight(1f)
        )

        Text(
            text = "×",

            color =
                textColor.copy(alpha = 0.75f),

            fontSize =
                20.sp,

            modifier = Modifier
                .clickable {
                    onClose()
                }
                .padding(
                    horizontal = 4.dp
                )
        )
    }
}