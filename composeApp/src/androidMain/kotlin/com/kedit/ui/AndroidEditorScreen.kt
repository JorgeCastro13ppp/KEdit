package com.kedit.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kedit.ui.components.EditorArea
import com.kedit.ui.components.EditorTabs
import com.kedit.ui.components.SearchBar
import com.kedit.ui.components.StatusBar
import com.kedit.ui.theme.KEditTheme
import com.kedit.viewmodel.EditorViewModel
import com.kedit.viewmodel.SearchViewModel

import com.kedit.ui.components.MobileTopBar
import com.kedit.ui.components.MobileEditorArea
import com.kedit.ui.components.MobileSearchBar
import com.kedit.ui.components.MobileSessionPanel
import com.kedit.ui.components.MobileStatusBar
import com.kedit.viewmodel.MobileSessionViewModel

@Composable
fun AndroidEditorScreen() {

    val viewModel = remember {
        EditorViewModel()
    }

    val searchViewModel = remember {
        SearchViewModel()
    }

    val state = viewModel.state

    val activeDocument = state.activeDocument

    val sessionViewModel = remember {
        MobileSessionViewModel()
    }

    var showAccountPanel by remember {
        mutableStateOf(false)
    }

    KEditTheme(
        darkTheme = viewModel.settings.isDarkMode
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                MobileTopBar(
                    documentName = activeDocument?.name ?: "Sin documento",

                    onNewDocument = {
                        viewModel.createDocument()
                    },

                    onToggleTheme = {
                        viewModel.toggleTheme()
                    },

                    onToggleSearch = {
                        searchViewModel.toggle()
                    },
                    onAccountClick = {
                        showAccountPanel = !showAccountPanel
                    }
                )

                if (showAccountPanel) {
                    MobileSessionPanel(
                        sessionViewModel = sessionViewModel
                    )
                }

                EditorTabs(
                    documents = state.openDocuments,
                    activeDocumentId = activeDocument?.id,

                    onSelectDocument = {
                        viewModel.switchDocument(it)
                    },

                    onCloseDocument = {
                        viewModel.closeDocument(it)
                    }
                )

                if (searchViewModel.state.isVisible) {

                    MobileSearchBar(
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
                        }
                    )
                }

                MobileEditorArea(
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
                MobileStatusBar(
                    activeDocument = activeDocument
                )
            }
        }
    }
}