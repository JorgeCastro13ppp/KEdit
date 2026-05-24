package com.kedit.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.kedit.ui.components.EditorArea
import com.kedit.ui.components.EditorTabs
import com.kedit.ui.components.SearchBar
import com.kedit.ui.components.StatusBar
import com.kedit.ui.components.VersionsScreen
import com.kedit.ui.components.WebSidebar
import com.kedit.ui.components.WebTopBar
import com.kedit.ui.theme.KEditTheme
import com.kedit.viewmodel.EditorViewModel
import com.kedit.viewmodel.SearchViewModel

@Composable
fun WebEditorScreen() {

    val viewModel = remember {
        EditorViewModel()
    }

    val searchViewModel = remember {
        SearchViewModel()
    }

    val state = viewModel.state
    val activeDocument = state.activeDocument

    var showVersions by remember {
        mutableStateOf(false)
    }
    var isSidebarVisible by remember {
        mutableStateOf(true)
    }

    var documentPendingCloseId by remember {
        mutableStateOf<String?>(null)
    }

    fun requestCloseDocument(id: String) {

        val document =
            viewModel.state.openDocuments.find {
                it.id == id
            }

        if (document?.isModified == true) {
            documentPendingCloseId = id
        } else {
            viewModel.closeDocument(id)
        }
    }

    KEditTheme(
        darkTheme = viewModel.settings.isDarkMode
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Row(
                modifier = Modifier.fillMaxSize()
            ) {

                if (isSidebarVisible) {

                    WebSidebar(
                        documents = state.openDocuments,
                        activeDocumentId = activeDocument?.id,
                        onSelectDocument = {
                            viewModel.switchDocument(it)
                        }
                    )
                }

                if (showVersions) {

                    VersionsScreen(
                        onBack = {
                            showVersions = false
                        }
                    )

                } else {

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) {

                        WebTopBar(
                            documentName =
                                activeDocument?.name ?: "Sin documento",

                            onNewDocument = {
                                viewModel.createDocument()
                            },

                            onToggleTheme = {
                                viewModel.toggleTheme()
                            },

                            onToggleSearch = {
                                searchViewModel.toggle()
                            },

                            onShowVersions = {
                                showVersions = true
                            },

                            onToggleSidebar = {
                                isSidebarVisible = !isSidebarVisible
                            }
                        )

                        EditorTabs(
                            documents = state.openDocuments,
                            activeDocumentId = activeDocument?.id,

                            onSelectDocument = {
                                viewModel.switchDocument(it)
                            },

                            onCloseDocument = {
                                requestCloseDocument(it)
                            }
                        )

                        if (searchViewModel.state.isVisible) {

                            SearchBar(
                                state = searchViewModel.state,

                                onQueryChange = {
                                    searchViewModel.updateQuery(
                                        query = it,
                                        content = activeDocument?.content ?: ""
                                    )
                                },

                                onNext = {
                                    searchViewModel.nextMatch()
                                },

                                onPrevious = {
                                    searchViewModel.previousMatch()
                                },

                                onToggleCaseSensitive = {
                                    searchViewModel.toggleCaseSensitive(
                                        content = activeDocument?.content ?: ""
                                    )
                                },

                                onClear = {
                                    searchViewModel.updateQuery(
                                        query = "",
                                        content = activeDocument?.content ?: ""
                                    )
                                },

                                onClose = {
                                    searchViewModel.hide()
                                },

                                onReplaceTextChange = {
                                    searchViewModel.updateReplaceText(it)
                                },

                                onReplaceCurrent = {

                                    val newContent =
                                        searchViewModel.replaceCurrent(
                                            content = activeDocument?.content ?: ""
                                        )

                                    viewModel.updateContent(newContent)

                                    searchViewModel.updateQuery(
                                        query = searchViewModel.state.query,
                                        content = newContent
                                    )
                                },

                                onReplaceAll = {

                                    val newContent =
                                        searchViewModel.replaceAll(
                                            content = activeDocument?.content ?: ""
                                        )

                                    viewModel.updateContent(newContent)

                                    searchViewModel.updateQuery(
                                        query = searchViewModel.state.query,
                                        content = newContent
                                    )
                                }
                            )
                        }

                        EditorArea(
                            modifier = Modifier.weight(1f),

                            content =
                                activeDocument?.content ?: "",

                            targetLine =
                                searchViewModel.currentMatch()?.line,

                            targetSearchIndex =
                                searchViewModel.state.currentIndex,

                            onContentChange = {
                                viewModel.updateContent(it)

                                searchViewModel.updateQuery(
                                    query = searchViewModel.state.query,
                                    content = it
                                )
                            }
                        )

                        StatusBar(
                            activeDocument = activeDocument,
                            isDarkMode = viewModel.settings.isDarkMode,
                            autoSaveEnabled = viewModel.settings.autoSaveEnabled
                        )
                    }
                }
            }
        }

        if (documentPendingCloseId != null) {

            AlertDialog(
                onDismissRequest = {
                    documentPendingCloseId = null
                },

                title = {
                    Text("Cerrar documento")
                },

                text = {
                    Text(
                        "El documento tiene cambios sin guardar. ¿Deseas cerrarlo igualmente?"
                    )
                },

                confirmButton = {
                    TextButton(
                        onClick = {

                            val id =
                                documentPendingCloseId

                            if (id != null) {
                                viewModel.closeDocument(id)
                            }

                            documentPendingCloseId = null
                        }
                    ) {
                        Text("Cerrar sin guardar")
                    }
                },

                dismissButton = {
                    TextButton(
                        onClick = {
                            documentPendingCloseId = null
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}