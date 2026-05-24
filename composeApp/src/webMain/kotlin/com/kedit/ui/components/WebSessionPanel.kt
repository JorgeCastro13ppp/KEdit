package com.kedit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kedit.viewmodel.WebSessionViewModel

@Composable
fun WebSessionPanel(
    sessionViewModel: WebSessionViewModel
) {

    var isRegisterMode by remember {
        mutableStateOf(false)
    }

    val currentUser =
        sessionViewModel.currentUser

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(12.dp)
    ) {

        if (currentUser == null) {

            Text(
                text = "Cuenta KEdit",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace
            )

            Spacer(Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Bottom
            ) {

                WebSessionInput(
                    label = "Email",
                    value = sessionViewModel.email,
                    onValueChange = sessionViewModel::updateEmail
                )

                WebSessionInput(
                    label = "Contraseña",
                    value = sessionViewModel.password,
                    onValueChange = sessionViewModel::updatePassword,
                    isPassword = true
                )

                if (isRegisterMode) {
                    WebSessionInput(
                        label = "Repetir contraseña",
                        value = sessionViewModel.confirmPassword,
                        onValueChange = sessionViewModel::updateConfirmPassword,
                        isPassword = true
                    )
                }

                WebSessionAction(
                    text = "Login",
                    onClick = {
                        isRegisterMode = false
                        sessionViewModel.login()
                    }
                )

                WebSessionAction(
                    text = "Registro",
                    onClick = {
                        if (!isRegisterMode) {
                            isRegisterMode = true
                        } else {
                            sessionViewModel.register()
                        }
                    }
                )
            }

        } else {

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Conectado: ${currentUser.email}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Monospace
                )

                WebSessionAction(
                    text = "Cerrar sesión",
                    onClick = {
                        sessionViewModel.logout()
                        isRegisterMode = false
                    }
                )
            }
        }

        if (sessionViewModel.isLoading) {

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Conectando...",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace
            )
        }

        if (sessionViewModel.message.isNotBlank()) {

            Spacer(Modifier.height(8.dp))

            Text(
                text = sessionViewModel.message,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
private fun WebSessionInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {

    Column {

        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace
        )

        Spacer(Modifier.height(4.dp))

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            visualTransformation =
                if (isPassword)
                    PasswordVisualTransformation()
                else
                    VisualTransformation.None,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace
            ),
            modifier = Modifier
                .width(200.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    horizontal = 8.dp,
                    vertical = 6.dp
                )
        )
    }
}

@Composable
private fun WebSessionAction(
    text: String,
    onClick: () -> Unit
) {

    Text(
        text = text,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 11.sp,
        fontFamily = FontFamily.Monospace,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f))
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 12.dp,
                vertical = 7.dp
            )
    )
}