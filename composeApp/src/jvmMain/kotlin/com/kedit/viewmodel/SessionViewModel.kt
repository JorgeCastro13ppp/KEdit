package com.kedit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kedit.remote.RemoteRepository
import com.kedit.remote.RemoteUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.kedit.remote.RemoteDocument

class SessionViewModel {

    private val remoteRepository =
        RemoteRepository()

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    fun updateConfirmPassword(
        value: String
    ) {
        confirmPassword = value
    }

    var currentUser by mutableStateOf<RemoteUser?>(null)
        private set

    var message by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var remoteDocuments by mutableStateOf<List<RemoteDocument>>(emptyList())
        private set

    fun saveRemoteDocument(
        remoteId: Int?,
        name: String,
        content: String,
        onSaved: () -> Unit = {}
    ) {

        val user =
            currentUser

        if (user == null) {
            message = "Inicia sesión para guardar en la nube"
            return
        }

        isLoading = true
        message = ""

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val saved =
                    if (remoteId == null) {
                        remoteRepository.saveDocument(
                            userId = user.userId,
                            name = name,
                            content = content
                        )
                    } else {
                        remoteRepository.updateDocument(
                            documentId = remoteId,
                            userId = user.userId,
                            name = name,
                            content = content
                        )
                    }

                if (saved) {
                    message = "Documento guardado en la nube"
                    onSaved()
                } else {
                    message = "No se pudo guardar el documento"
                }

            } catch (e: Exception) {
                message = "Error al guardar en la nube"
            } finally {
                isLoading = false
            }
        }
    }

    fun loadRemoteDocuments() {

        val user =
            currentUser

        if (user == null) {
            message = "Inicia sesión para ver documentos remotos"
            return
        }

        isLoading = true
        message = ""

        CoroutineScope(Dispatchers.IO).launch {

            try {

                remoteDocuments =
                    remoteRepository.listDocuments(
                        userId = user.userId
                    )

                message =
                    if (remoteDocuments.isEmpty())
                        "No hay documentos remotos"
                    else
                        "Documentos remotos cargados"

            } catch (e: Exception) {
                message = "Error al cargar documentos remotos"
            } finally {
                isLoading = false
            }
        }
    }

    fun openRemoteDocument(
        documentId: Int,
        onLoaded: (String, String) -> Unit
    ) {

        isLoading = true
        message = ""

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val document =
                    remoteRepository.getDocument(
                        documentId = documentId
                    )

                if (document != null) {
                    onLoaded(
                        document.name,
                        document.content
                    )

                    message = "Documento remoto abierto"
                } else {
                    message = "Documento remoto no encontrado"
                }

            } catch (e: Exception) {
                message = "Error al abrir documento remoto"
            } finally {
                isLoading = false
            }
        }
    }

    fun updateEmail(
        value: String
    ) {
        email = value
    }

    fun updatePassword(
        value: String
    ) {
        password = value
    }

    fun login() {

        if (email.isBlank() || password.isBlank()) {
            message = "Introduce email y contraseña"
            return
        }

        isLoading = true
        message = ""

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val user =
                    remoteRepository.login(
                        email = email,
                        password = password
                    )

                if (user != null) {
                    currentUser = user
                    message = "Sesión iniciada: ${user.email}"
                } else {
                    message = "Credenciales incorrectas"
                }

            } catch (e: Exception) {
                message = "No se pudo conectar con el backend"
            } finally {
                isLoading = false
            }
        }
    }

    fun register() {

        if (email.isBlank() || password.isBlank()) {
            message = "Introduce email y contraseña"
            return
        }

        if (password != confirmPassword) {
            message = "Las contraseñas no coinciden"
            return
        }

        isLoading = true
        message = ""

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val user =
                    remoteRepository.register(
                        email = email,
                        password = password
                    )

                if (user != null) {
                    currentUser = user
                    message = "Usuario registrado: ${user.email}"
                } else {
                    message = "No se pudo registrar el usuario"
                }

            } catch (e: Exception) {
                message = "No se pudo conectar con el backend"
            } finally {
                isLoading = false
            }
        }
    }

    fun logout() {
        currentUser = null
        password = ""
        message = "Sesión cerrada"
    }
}