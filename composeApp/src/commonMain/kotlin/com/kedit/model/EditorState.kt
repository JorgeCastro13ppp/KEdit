package com.kedit.model

data class EditorState(
    val openDocuments: List<Document> = emptyList(),
    var activeDocument: Document? = null
)