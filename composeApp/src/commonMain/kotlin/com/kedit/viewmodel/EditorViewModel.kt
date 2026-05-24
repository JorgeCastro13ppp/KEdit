package com.kedit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kedit.model.Document
import com.kedit.model.EditorState
import com.kedit.model.FileItem
import com.kedit.model.Settings
import kotlin.time.Clock
import kotlinx.coroutines.*
import com.kedit.repository.FileRepository
import com.kedit.repository.SettingsRepository


class EditorViewModel {

    private val scope =
        CoroutineScope(Dispatchers.Main)

    private val repository =
        FileRepository()

    private val settingsRepository =
        SettingsRepository()
    private var autoSaveJob: Job? = null

    var state by mutableStateOf(EditorState())
        private set

    var settings by mutableStateOf(
        settingsRepository.loadSettings()
    )
        private set

    init {
        createDocument()
        loadLastDirectory()
    }

    private fun loadLastDirectory() {

        val directory =
            settings.lastDirectory ?: return

        val files =
            repository.listFiles(directory)

        state = state.copy(
            currentDirectory = directory,
            files = files
        )
    }


    fun createDocument() {

        val documentNumber = state.openDocuments.size + 1



        val doc = Document(
            name = "Document $documentNumber",
            path = "document$documentNumber.txt"
        )

        val updatedDocuments =
            state.openDocuments + doc

        state = state.copy(
            openDocuments = updatedDocuments,
            activeDocument = doc
        )

    }

    fun switchDocument(id: String) {

        val document = state.openDocuments.find {
            it.id == id
        }

        state = state.copy(
            activeDocument = document
        )
    }

    fun closeDocument(id: String) {

        val updatedDocuments =
            state.openDocuments.filter {
                it.id != id
            }

        val newActiveDocument =
            updatedDocuments.lastOrNull()

        state = state.copy(
            openDocuments = updatedDocuments,
            activeDocument = newActiveDocument
        )
    }

    fun updateContent(newContent: String) {

        val active = state.activeDocument ?: return

        val updatedDocument = active.copy(
            content = newContent,
            isModified = true,
            lastModified =
                Clock.System.now().toEpochMilliseconds()
        )

        val updatedDocuments =
            state.openDocuments.map {

                if (it.id == updatedDocument.id)
                    updatedDocument
                else
                    it
            }.toMutableList()

        state = state.copy(
            openDocuments = updatedDocuments,
            activeDocument = updatedDocument
        )

        autoSave()
    }

    fun saveActiveDocument() {

        val active =
            state.activeDocument ?: return

        val path =
            active.path ?: return

        repository.saveFile(
            path,
            active.content
        )

        val savedDocument =
            active.copy(
                isModified = false
            )

        val updatedDocuments =
            state.openDocuments.map {

                if (it.id == savedDocument.id)
                    savedDocument
                else
                    it
            }

        state = state.copy(
            openDocuments = updatedDocuments,
            activeDocument = savedDocument
        )
    }

    private fun autoSave() {

        if (!settings.autoSaveEnabled)
            return

        autoSaveJob?.cancel()

        autoSaveJob = scope.launch {

            delay(20000)

            val active =
                state.activeDocument ?: return@launch

            val path =
                active.path ?: return@launch

            repository.saveFile(
                path = path,
                content = active.content
            )

            val savedDocument =
                active.copy(
                    isModified = false
                )

            val updatedDocuments =
                state.openDocuments.map { document ->

                    if (document.id == savedDocument.id)
                        savedDocument
                    else
                        document
                }

            state = state.copy(
                openDocuments = updatedDocuments,
                activeDocument = savedDocument
            )
        }
    }
    fun toggleTheme() {

        settings = settings.copy(
            isDarkMode = !settings.isDarkMode
        )

        settingsRepository.saveSettings(settings)
    }

    fun toggleAutoSave(): Boolean {

        settings = settings.copy(
            autoSaveEnabled = !settings.autoSaveEnabled
        )

        settingsRepository.saveSettings(settings)

        return settings.autoSaveEnabled
    }

    fun openFile() {

        val path =
            repository.pickFile()
                ?: return

        val content =
            repository.readFile(path)

        val fileName =
            repository.getFileName(path)

        val document =
            Document(
                name = fileName,
                path = path,
                content = content,
                isModified = false
            )

        val updatedDocuments =
            state.openDocuments + document

        state = state.copy(
            openDocuments = updatedDocuments,
            activeDocument = document
        )
    }

    fun closeActiveDocument() {

        val active =
            state.activeDocument ?: return

        closeDocument(active.id)
    }

    fun saveActiveDocumentAs() {

        val active =
            state.activeDocument ?: return

        val path =
            repository.pickSaveFile() ?: return

        repository.saveFile(
            path = path,
            content = active.content
        )

        val updatedDocument =
            active.copy(
                name = repository.getFileName(path),
                path = path,
                isModified = false
            )

        val updatedDocuments =
            state.openDocuments.map { document ->

                if (document.id == updatedDocument.id)
                    updatedDocument
                else
                    document
            }

        state = state.copy(
            openDocuments = updatedDocuments,
            activeDocument = updatedDocument
        )
    }

    fun openDirectory() {

        val directoryPath =
            repository.pickDirectory() ?: return

        val files =
            repository.listFiles(directoryPath)

        state = state.copy(
            currentDirectory = directoryPath,
            files = files
        )

        settings = settings.copy(
            lastDirectory = directoryPath
        )

        settingsRepository.saveSettings(settings)
    }

    fun openFileFromExplorer(
        path: String
    ) {

        val alreadyOpen =
            state.openDocuments.find { document ->
                document.path == path
            }

        if (alreadyOpen != null) {

            state = state.copy(
                activeDocument = alreadyOpen
            )

            return
        }

        val content =
            repository.readFile(path)

        val fileName =
            repository.getFileName(path)

        val document =
            Document(
                name = fileName,
                path = path,
                content = content,
                isModified = false
            )

        val updatedDocuments =
            state.openDocuments + document

        state = state.copy(
            openDocuments = updatedDocuments,
            activeDocument = document
        )
    }

    fun openExplorerItem(
        fileItem: FileItem
    ) {

        if (fileItem.isDirectory) {

            val files =
                repository.listFiles(fileItem.path)

            state = state.copy(
                currentDirectory = fileItem.path,
                files = files
            )

            settings = settings.copy(
                lastDirectory = fileItem.path
            )

            settingsRepository.saveSettings(settings)

        } else {

            openFileFromExplorer(fileItem.path)
        }
    }

    fun goToParentDirectory() {

        val current =
            state.currentDirectory ?: return

        val parent =
            repository.getParentDirectory(current) ?: return

        val files =
            repository.listFiles(parent)

        state = state.copy(
            currentDirectory = parent,
            files = files
        )

        settings = settings.copy(
            lastDirectory = parent
        )

        settingsRepository.saveSettings(settings)
    }

    fun toggleExplorerVisibility() {

        state = state.copy(
            isExplorerVisible = !state.isExplorerVisible
        )
    }
}