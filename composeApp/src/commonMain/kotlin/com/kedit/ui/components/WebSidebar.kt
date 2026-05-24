package com.kedit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun WebSidebar(
    documents: List<Document>,
    activeDocumentId: String?,
    onSelectDocument: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .width(230.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(10.dp)
    ) {

        Text(
            text = "Documentos",
            fontSize = 14.sp,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        documents.forEach { document ->

            val isActive =
                document.id == activeDocumentId

            val background =
                if (isActive)
                    MaterialTheme.colorScheme.primaryContainer
                else
                    MaterialTheme.colorScheme.surfaceVariant

            Text(
                text =
                    document.name +
                            if (document.isModified) " *" else "",

                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurfaceVariant,

                modifier = Modifier
                    .fillMaxWidth()
                    .background(background)
                    .clickable {
                        onSelectDocument(document.id)
                    }
                    .padding(
                        horizontal = 8.dp,
                        vertical = 10.dp
                    )
            )
        }
    }
}