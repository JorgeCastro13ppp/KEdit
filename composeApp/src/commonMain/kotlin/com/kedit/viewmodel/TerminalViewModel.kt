package com.kedit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kedit.model.TerminalState

class TerminalViewModel {

    var state by mutableStateOf(TerminalState())
        private set

    fun updateInput(input: String) {

        state = state.copy(
            currentInput = input
        )
    }

    fun executeCommand(
        onNewDocument: () -> Unit,
        onSaveDocument: () -> Unit,
        onToggleTheme: () -> Unit,
        onCloseDocument: () -> Unit,
        getOpenDocumentsInfo: () -> List<String>,
        onToggleAutoSave: () -> Boolean,
        onToggleTerminal: () -> Unit,
        onSaveDocumentAs: () -> Unit,
        onToggleSearch: () -> Unit,
        onSearchText: (String) -> Unit
    ) {

        val command =
            state.currentInput.trim()

        val commandName =
            command.substringBefore(" ")

        val argument =
            command.substringAfter(
                delimiter = " ",
                missingDelimiterValue = ""
            )

        if (command.isBlank())
            return

        when (commandName) {

            "clear" -> {

                state = state.copy(
                    history = emptyList(),
                    currentInput = ""
                )

                return
            }
        }

        val output =
            when (commandName) {

                "help" ->
                    """
                    Comandos disponibles:
                    help      - Muestra esta ayuda
                    clear     - Limpia la terminal
                    version   - Muestra la versión de KEdit
                    new       - Crea un nuevo documento
                    save      - Guarda el documento activo
                    docs      - Lista los documentos abiertos
                    theme     - Cambia entre tema claro y oscuro
                    autosave  - Activa o desactiva el autoguardado
                    close     - Cierra el documento activo
                    terminal  - Muestra u oculta la terminal
                    saveas    - Guarda el documento activo en una nueva ubicación
                    search    - Muestra la barra de búsqueda
                    search x  - Busca el texto indicado
                    """.trimIndent()

                "version" ->
                    "KEdit Terminal v1.0"

                "new" -> {

                    onNewDocument()

                    "Nuevo documento creado"
                }

                "save" -> {

                    onSaveDocument()

                    "Documento guardado"
                }
                "saveas" -> {

                    onSaveDocumentAs()

                    "Guardar como ejecutado"
                }

                "theme" -> {

                    onToggleTheme()

                    "Tema cambiado"
                }

                "close" -> {

                    onCloseDocument()

                    "Documento activo cerrado"
                }

                "docs" -> {

                    val documents =
                        getOpenDocumentsInfo()

                    if (documents.isEmpty()) {

                        "No hay documentos abiertos"

                    } else {

                        documents.joinToString(
                            separator = "\n"
                        )
                    }
                }

                "autosave" -> {

                    val enabled =
                        onToggleAutoSave()

                    if (enabled)
                        "Autoguardado activado"
                    else
                        "Autoguardado desactivado"
                }

                "terminal" -> {

                    onToggleTerminal()

                    "Terminal alternada"
                }

                "search" -> {

                    if (argument.isBlank()) {

                        onToggleSearch()

                        "Barra de búsqueda alternada"

                    } else {

                        onSearchText(argument)

                        "Buscando: $argument"
                    }
                }

                else ->
                    "Comando no reconocido: $command"
            }

        state = state.copy(
            history =
                state.history +
                        listOf(
                            "> $command",
                            output
                        ),

            currentInput = ""
        )
    }

    fun toggleVisibility() {

        state = state.copy(
            isVisible = !state.isVisible
        )
    }

    fun increaseHeight() {

        state = state.copy(
            height = state.height + 40f
        )
    }

    fun decreaseHeight() {

        state = state.copy(
            height =
                (state.height - 40f)
                    .coerceAtLeast(120f)
        )
    }
}