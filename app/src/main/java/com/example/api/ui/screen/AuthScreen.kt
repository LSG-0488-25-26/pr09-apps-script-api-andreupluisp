package com.example.api.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.api.ui.state.AuthUiState

@Composable
fun AuthScreen(
    uiState: AuthUiState,
    modifier: Modifier = Modifier,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onShowLoginClick: () -> Unit,
    onShowRegisterClick: () -> Unit
) {
    // Estado local para controlar si la contraseña es visible o no
    var passwordVisible by remember { mutableStateOf(false) }

    // Validacion de requisitos de contraseña: 3 letras, 2 numeros, 1 especial
    val isPasswordValid = if (uiState.isRegisterMode) {
        val letters = uiState.password.count { it.isLetter() }
        val digits = uiState.password.count { it.isDigit() }
        val specials = uiState.password.count { !it.isLetterOrDigit() && !it.isWhitespace() }
        letters >= 3 && digits >= 2 && specials >= 1
    } else {
        uiState.password.isNotEmpty()
    }

    // Comprobacion general para habilitar el boton de accion
    val canSubmit = uiState.username.isNotBlank() && 
            isPasswordValid && 
            (!uiState.isRegisterMode || (uiState.password == uiState.confirmPassword && uiState.confirmPassword.isNotEmpty()))

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Titulo de la pantalla segun el modo (login o registro)
        Text(
            text = if (uiState.isRegisterMode) "Registre local" else "Inici de sessi\u00F3",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Descripcion del almacenamiento
        Text(
            text = "Les credencials es guarden amb SharedPrefs dins del dispositiu",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo para introducir el nombre de usuario
        OutlinedTextField(
            value = uiState.username,
            onValueChange = onUsernameChange,
            label = { Text("Usuari") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo para introducir la contraseña con icono para mostrar/ocultar
        OutlinedTextField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            label = { Text("Contrasenya") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "Ocultar contrasenya" else "Mostrar contrasenya"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
            singleLine = true,
            supportingText = if (uiState.isRegisterMode) {
                { Text("Requerit: 3 lletres, 2 números i 1 símbol") }
            } else null
        )

        // Campo para confirmar contraseña con el mismo control de visibilidad
        if (uiState.isRegisterMode) {
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = { Text("Repeteix la contrasenya") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Ocultar contrasenya" else "Mostrar contrasenya"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                },
                singleLine = true,
                isError = uiState.password != uiState.confirmPassword && uiState.confirmPassword.isNotEmpty()
            )
        }

        // Mensajes de error si los hay
        uiState.errorMessage?.let { message ->
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Mensajes de exito si los hay
        uiState.successMessage?.let { message ->
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = message,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Boton principal de accion (Entrar o Registrar)
        Button(
            onClick = if (uiState.isRegisterMode) onRegisterClick else onLoginClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = canSubmit
        ) {
            Text(text = if (uiState.isRegisterMode) "Registrar" else "Entrar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Boton para cambiar entre modo login y modo registro
        TextButton(
            onClick = if (uiState.isRegisterMode) onShowLoginClick else onShowRegisterClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (uiState.isRegisterMode) {
                    "Ja hi ha un usuari guardat? Ves a l'inici de sessi\u00F3"
                } else {
                    "No hi ha cap usuari? Crear registre"
                }
            )
        }
    }
}
