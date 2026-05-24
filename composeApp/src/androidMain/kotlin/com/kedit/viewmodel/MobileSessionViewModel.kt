package com.kedit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kedit.remote.AndroidRemoteRepository
import com.kedit.remote.AndroidRemoteUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MobileSessionViewModel {

    private val remoteRepository =
        AndroidRemoteRepository()

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var currentUser by mutableStateOf<AndroidRemoteUser?>(null)
        private set

    var message by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun updateEmail(value: String) {
        email = value
    }

    fun updatePassword(value: String) {
        password = value
    }

    fun updateConfirmPassword(value: String) {
        confirmPassword = value
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
        confirmPassword = ""
        message = "Sesión cerrada"
    }
}