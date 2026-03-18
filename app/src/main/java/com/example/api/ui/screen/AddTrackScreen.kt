package com.example.api.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.api.ui.state.AddTrackUiState

@Composable
fun AddTrackScreen(
    username: String,
    uiState: AddTrackUiState,
    modifier: Modifier = Modifier,
    onTrackNameChange: (String) -> Unit,
    onTrackArtistChange: (String) -> Unit,
    onPlaylistGenreChange: (String) -> Unit,
    onTrackAlbumReleaseDateChange: (String) -> Unit,
    onTrackAlbumNameChange: (String) -> Unit,
    onTrackPopularityChange: (String) -> Unit,
    onUriChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onBackToListClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Afegir track",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Usuari: $username",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(16.dp))

                FilledTonalButton(
                    onClick = onBackToListClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Tornar a la llista")
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = onLogoutClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Tancar sessio")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Omple les dades basiques de la nova canco",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        FormField(
            value = uiState.trackName,
            onValueChange = onTrackNameChange,
            label = "Nom de la canco"
        )

        FormField(
            value = uiState.trackArtist,
            onValueChange = onTrackArtistChange,
            label = "Artista"
        )

        FormField(
            value = uiState.playlistGenre,
            onValueChange = onPlaylistGenreChange,
            label = "Genere"
        )

        FormField(
            value = uiState.trackAlbumReleaseDate,
            onValueChange = onTrackAlbumReleaseDateChange,
            label = "Data album",
            supportingText = "Format YYYY-MM-DD"
        )

        FormField(
            value = uiState.trackAlbumName,
            onValueChange = onTrackAlbumNameChange,
            label = "Nom album"
        )

        FormField(
            value = uiState.trackPopularity,
            onValueChange = onTrackPopularityChange,
            label = "Popularitat"
        )

        FormField(
            value = uiState.uri,
            onValueChange = onUriChange,
            label = "Uri spotify"
        )

        uiState.errorMessage?.let { message ->
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error
            )
        }

        uiState.successMessage?.let { message ->
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = message,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onSaveClick,
            enabled = !uiState.isSaving,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState.isSaving) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(18.dp)
                        .padding(end = 8.dp),
                    strokeWidth = 2.dp
                )
            }
            Text(text = if (uiState.isSaving) "Guardant..." else "Guardar canco")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun FormField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    supportingText: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        supportingText = {
            if (supportingText != null) {
                Text(text = supportingText)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(12.dp))
}
