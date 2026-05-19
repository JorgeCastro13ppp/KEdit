package com.kedit.ui

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
import com.kedit.viewmodel.SearchViewModel
import com.kedit.ui.components.SearchBar
@Composable
fun EditorScreen() {

    val viewModel = remember {
        EditorViewModel()
    }

    val state = viewModel.state

    val activeDocument = state.activeDocument

    val terminalViewModel = remember {
        TerminalViewModel()
    }

    val searchViewModel = remember {
        SearchViewModel()
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

    fun requestCloseActiveDocument() {

        val active =
            viewModel.state.activeDocument ?: return

        requestCloseDocument(active.id)
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
                        viewModel.saveActiveDocument()
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
                    }
                ),

            color = MaterialTheme.colorScheme.background
        ) {

            Row {

                Sidebar(
                    documents = state.openDocuments,
                    activeDocumentId =
                        activeDocument?.id,

                    onSelectDocument = {
                        viewModel.switchDocument(it)
                    }
                )

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
                        viewModel.saveActiveDocument()
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
                            onReplacementChange = {
                                searchViewModel.updateReplacement(it)
                            },

                            onReplaceCurrent = {

                                val content =
                                    activeDocument?.content ?: return@SearchBar

                                val newContent =
                                    searchViewModel.replaceCurrent(content)

                                viewModel.updateContent(newContent)
                            },

                            onReplaceAll = {

                                val content =
                                    activeDocument?.content ?: return@SearchBar

                                val newContent =
                                    searchViewModel.replaceAll(content)

                                viewModel.updateContent(newContent)
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

                            onInputChange = {
                                terminalViewModel.updateInput(it)
                            },

                            onExecute = {
                                terminalViewModel.executeCommand(

                                    onNewDocument = {
                                        viewModel.createDocument()
                                    },

                                    onSaveDocument = {
                                        viewModel.saveActiveDocument()
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