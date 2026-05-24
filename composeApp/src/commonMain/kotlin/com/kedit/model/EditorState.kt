package com.kedit.model

data class EditorState(
    val openDocuments: List<Document> = emptyList(),
    var activeDocument: Document? = null,
    val currentDirectory: String? = null,
    val files: List<FileItem> = emptyList(),
    val isExplorerVisible: Boolean = true
)