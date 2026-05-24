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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kedit.viewmodel.SessionViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun SessionPanel(
    sessionViewModel: SessionViewModel
) {

    val currentUser =
        sessionViewModel.currentUser

    var isRegisterMode by remember {
        mutableStateOf(false)
    }

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

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Bottom
            ) {

                SessionInput(
                    label = "Email",
                    value = sessionViewModel.email,
                    onValueChange = sessionViewModel::updateEmail
                )

                SessionInput(
                    label = "Contraseña",
                    value = sessionViewModel.password,
                    onValueChange = sessionViewModel::updatePassword,
                    isPassword = true
                )

                if (isRegisterMode) {
                    SessionInput(
                        label = "Repetir contraseña",
                        value = sessionViewModel.confirmPassword,
                        onValueChange = sessionViewModel::updateConfirmPassword,
                        isPassword = true
                    )
                }

                SessionAction(
                    text = "Login",
                    onClick = {
                        isRegisterMode = false
                        sessionViewModel.login()
                    }
                )

                SessionAction(
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

                SessionAction(
                    text = "Cerrar sesión",
                    onClick = {
                        sessionViewModel.logout()
                    }
                )
            }
        }

        if (sessionViewModel.message.isNotBlank()) {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

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
private fun SessionInput(
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

        Spacer(
            modifier = Modifier.height(4.dp)
        )

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
                .width(220.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    horizontal = 8.dp,
                    vertical = 6.dp
                )
        )
    }
}

@Composable
private fun SessionAction(
    text: String,
    onClick: () -> Unit
) {

    Text(
        text = text,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 12.sp,
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