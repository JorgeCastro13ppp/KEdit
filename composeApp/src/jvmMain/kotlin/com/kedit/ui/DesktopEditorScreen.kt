package com.kedit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.kedit.ui.components.EditorArea
import com.kedit.ui.components.EditorTabs
import com.kedit.ui.components.TopBar
import com.kedit.viewmodel.EditorViewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import com.kedit.ui.components.Sidebar
import com.kedit.ui.components.TerminalPanel
import com.kedit.ui.theme.KEditTheme
import com.kedit.viewmodel.TerminalViewModel
import com.kedit.ui.components.StatusBar
import com.kedit.ui.components.keditKeyboardShortcuts
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.unit.dp
import com.kedit.ui.components.FileExplorer
import com.kedit.ui.components.RemoteDocumentsPanel
import com.kedit.viewmodel.SearchViewModel
import com.kedit.ui.components.SearchBar
import com.kedit.ui.components.SessionPanel
import com.kedit.viewmodel.SessionViewModel

@Composable
fun DesktopEditorScreen() {

    val viewModel = remember {
        EditorViewModel()
    }

    val state = viewModel.state

    val activeDocument = state.activeDocument

    val sessionViewModel = remember {
        SessionViewModel()
    }

    val terminalViewModel = remember {
        TerminalViewModel()
    }

    val searchViewModel = remember {
        SearchViewModel()
    }

    var showAccountPanel by remember {
        mutableStateOf(false)
    }

    var documentPendingCloseId by remember {
        mutableStateOf<String?>(null)
    }

    var showRemoteDocuments by remember {
        mutableStateOf(false)
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

    fun requestCloseActiveDocument() {

        val active =
            viewModel.state.activeDocument ?: return

        requestCloseDocument(active.id)
    }

    fun saveCurrentDocument() {

        val document =
            viewModel.state.activeDocument ?: return

        if (
            sessionViewModel.currentUser != null &&
            document.remoteId != null
        ) {

            sessionViewModel.saveRemoteDocument(
                remoteId = document.remoteId,
                name = document.name,
                content = document.content,
                onSaved = {
                    viewModel.markActiveDocumentAsSaved()
                }
            )

        } else {

            viewModel.saveActiveDocument()
        }
    }

    KEditTheme(
        darkTheme = viewModel.settings.isDarkMode
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .keditKeyboardShortcuts(

                    onNewDocument = {
                        viewModel.createDocument()
                    },

                    onSaveDocument = {
                        saveCurrentDocument()
                    },

                    onOpenDocument = {
                        viewModel.openFile()
                    },

                    onCloseDocument = {
                        requestCloseActiveDocument()
                    },

                    onToggleTheme = {
                        viewModel.toggleTheme()
                    },

                    onToggleTerminal = {
                        terminalViewModel.toggleVisibility()
                    },
                    onSaveDocumentAs = {
                        viewModel.saveActiveDocumentAs()
                    },
                    onToggleSearch = {
                        searchViewModel.toggle()
                    },
                    onGoToParentDirectory = {
                        viewModel.goToParentDirectory()
                    },
                    onToggleExplorer = {
                        viewModel.toggleExplorerVisibility()
                    }
                ),

            color = MaterialTheme.colorScheme.background
        ) {

            Row {

                if (state.isExplorerVisible) {

                    Column(
                        modifier = Modifier
                            .width(280.dp)
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    ) {

                        Sidebar(
                            modifier = Modifier.height(180.dp),
                            documents = state.openDocuments,
                            activeDocumentId = activeDocument?.id,
                            onSelectDocument = {
                                viewModel.switchDocument(it)
                            }
                        )

                        FileExplorer(
                            modifier = Modifier.weight(1f),
                            currentDirectory = state.currentDirectory,
                            files = state.files,
                            onOpenItem = {
                                viewModel.openExplorerItem(it)
                            },
                            onGoBack = {
                                viewModel.goToParentDirectory()
                            }
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    TopBar(
                        documentName =
                            activeDocument?.name ?: "Sin documento",

                        onNewDocument = {
                            viewModel.createDocument()
                        },
                        onSaveDocument = {
                            saveCurrentDocument()
                        },

                        onToggleTheme = {
                            viewModel.toggleTheme()
                        },
                        onOpenDocument = {
                            viewModel.openFile()
                        },
                        onToggleTerminal = {
                            terminalViewModel.toggleVisibility()
                        },
                        isTerminalVisible =
                            terminalViewModel.state.isVisible,

                        onSaveAsDocument = {
                            viewModel.saveActiveDocumentAs()
                        },
                        onToggleSearch = {
                            searchViewModel.toggle()
                        },
                        onOpenDirectory = {
                            viewModel.openDirectory()
                        },
                        onToggleExplorer = {
                            viewModel.toggleExplorerVisibility()
                        },

                        isExplorerVisible =
                            state.isExplorerVisible,

                        onAccountClick = {
                            showAccountPanel = !showAccountPanel
                        },
                        onSaveRemoteClick =
                            if (sessionViewModel.currentUser != null) {
                                {
                                    val document = viewModel.state.activeDocument

                                    if (document != null) {
                                        sessionViewModel.saveRemoteDocument(
                                            remoteId = document.remoteId,
                                            name = document.name,
                                            content = document.content,
                                            onSaved = {
                                                viewModel.markActiveDocumentAsSaved()
                                            }
                                        )
                                    }
                                }
                            } else {
                                null
                            },

                        onRemoteDocumentsClick =
                            if (sessionViewModel.currentUser != null) {
                                {
                                    showRemoteDocuments = !showRemoteDocuments

                                    if (showRemoteDocuments) {
                                        sessionViewModel.loadRemoteDocuments()
                                    }
                                }
                            } else {
                                null
                            }

                    )

                    if (showAccountPanel) {
                        SessionPanel(
                            sessionViewModel = sessionViewModel
                        )
                    }

                    if (showRemoteDocuments) {
                        RemoteDocumentsPanel(
                            documents = sessionViewModel.remoteDocuments,
                            onOpenDocument = { documentId ->

                                sessionViewModel.openRemoteDocument(
                                    documentId = documentId
                                ) { name, content ->

                                    viewModel.openRemoteDocument(
                                        remoteId = documentId,
                                        name = name,
                                        content = content
                                    )
                                }
                            }
                        )
                    }

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
                            },
                            onReplaceTextChange = {
                                searchViewModel.updateReplaceText(it)
                            },
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

                    if (terminalViewModel.state.isVisible) {

                        TerminalPanel(
                            state = terminalViewModel.state,
                            currentDirectory = state.currentDirectory,

                            onInputChange = {
                                terminalViewModel.updateInput(it)
                            },

                            onExecute = {
                                terminalViewModel.executeCommand(

                                    onNewDocument = {
                                        viewModel.createDocument()
                                    },

                                    onSaveDocument = {
                                        saveCurrentDocument()
                                    },

                                    onToggleTheme = {
                                        viewModel.toggleTheme()
                                    },

                                    onToggleAutoSave = {
                                        viewModel.toggleAutoSave()
                                    },

                                    onCloseDocument = {
                                        requestCloseActiveDocument()
                                    },

                                    getOpenDocumentsInfo = {

                                        viewModel.state.openDocuments.map { document ->

                                            val modifiedMark =
                                                if (document.isModified)
                                                    "*"
                                                else
                                                    ""

                                            "${document.name}$modifiedMark"
                                        }
                                    },
                                    onToggleTerminal = {
                                        terminalViewModel.toggleVisibility()
                                    },
                                    onSaveDocumentAs = {
                                        viewModel.saveActiveDocumentAs()
                                    },
                                    onToggleSearch = {
                                        searchViewModel.toggle()
                                    },
                                    onSearchText = { query ->

                                        searchViewModel.searchText(
                                            query = query,
                                            content = activeDocument?.content ?: ""
                                        )
                                    },
                                )
                            },

                            onIncreaseHeight = {
                                terminalViewModel.increaseHeight()
                            },

                            onDecreaseHeight = {
                                terminalViewModel.decreaseHeight()
                            }
                        )
                    }

                    StatusBar(
                        activeDocument = activeDocument,
                        isDarkMode = viewModel.settings.isDarkMode,
                        autoSaveEnabled = viewModel.settings.autoSaveEnabled
                    )
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
    }}