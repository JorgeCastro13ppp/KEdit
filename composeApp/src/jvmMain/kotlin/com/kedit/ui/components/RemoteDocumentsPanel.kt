package com.kedit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kedit.remote.RemoteDocument

@Composable
fun RemoteDocumentsPanel(
    documents: List<RemoteDocument>,
    onOpenDocument: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(12.dp)
    ) {

        Text(
            text = "Documentos remotos",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 13.sp,
            fontFamily = FontFamily.Monospace
        )

        documents.forEach { document ->

            Text(
                text = document.name,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable {
                        onOpenDocument(document.id)
                    }
            )
        }
    }
}