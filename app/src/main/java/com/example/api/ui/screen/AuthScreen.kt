package com.example.api.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
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

        // Campo para introducir la contraseña
        OutlinedTextField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            label = { Text("Contrasenya") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        // Campo para confirmar contraseña, solo visible en modo registro
        if (uiState.isRegisterMode) {
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = { Text("Repeteix la contrasenya") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
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
            modifier = Modifier.fillMaxWidth()
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
