package com.kedit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class WebSessionViewModel {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var currentUser by mutableStateOf<WebDemoUser?>(null)
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

        currentUser = WebDemoUser(email = email)
        message = "Sesión iniciada en modo demo: $email"
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

        currentUser = WebDemoUser(email = email)
        message = "Usuario registrado en modo demo: $email"
    }

    fun logout() {
        currentUser = null
        password = ""
        confirmPassword = ""
        message = "Sesión cerrada"
    }
}

data class WebDemoUser(
    val email: String
)