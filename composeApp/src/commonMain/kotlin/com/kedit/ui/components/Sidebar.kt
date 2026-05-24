package com.kedit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kedit.model.Document

@Composable
fun Sidebar(
    modifier: Modifier = Modifier,
    documents: List<Document>,
    activeDocumentId: String?,
    onSelectDocument: (String) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .padding(8.dp)
    ) {

        Text(
            text = "Documentos",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {

            items(documents) { document ->

                val isActive =
                    document.id == activeDocumentId

                val background =
                    if (isActive)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.surfaceVariant

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(background)
                        .clickable {
                            onSelectDocument(document.id)
                        }
                        .padding(12.dp)
                ) {

                    Text(
                        text =
                            document.name +
                                    if (document.isModified)
                                        "*"
                                    else
                                        ""
                    )
                }

                Spacer(
                    modifier = Modifier.height(4.dp)
                )
            }
        }
    }
}