package com.kedit.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kedit.model.Document

@Composable
fun EditorTabs(
    documents: List<Document>,
    activeDocumentId: String?,
    onSelectDocument: (String) -> Unit,
    onCloseDocument: (String) -> Unit
) {

    Row(
        modifier = androidx.compose.ui.Modifier
            .horizontalScroll(rememberScrollState()).fillMaxWidth()
    ) {

        documents.forEach { document ->

            TabItem(
                document = document,
                isActive = document.id == activeDocumentId,
                onClick = {
                    onSelectDocument(document.id)
                },
                onClose = {
                    onCloseDocument(document.id)
                }
            )
        }
    }
}