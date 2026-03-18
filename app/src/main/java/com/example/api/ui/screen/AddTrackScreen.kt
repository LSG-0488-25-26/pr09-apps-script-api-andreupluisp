package com.example.api.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
        Text(
            text = "Usuari: $username",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onBackToListClick,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Tornar")
            }

            Button(
                onClick = onLogoutClick,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Logout")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Afegir canco",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.trackName,
            onValueChange = onTrackNameChange,
            label = { Text("Nom de la canco") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.trackArtist,
            onValueChange = onTrackArtistChange,
            label = { Text("Artista") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.playlistGenre,
            onValueChange = onPlaylistGenreChange,
            label = { Text("Genere") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.trackAlbumReleaseDate,
            onValueChange = onTrackAlbumReleaseDateChange,
            label = { Text("Data album") },
            placeholder = { Text("2026-03-18") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.trackAlbumName,
            onValueChange = onTrackAlbumNameChange,
            label = { Text("Nom album") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.trackPopularity,
            onValueChange = onTrackPopularityChange,
            label = { Text("Popularitat") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.uri,
            onValueChange = onUriChange,
            label = { Text("Uri spotify") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
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
                        .height(18.dp)
                        .padding(end = 8.dp),
                    strokeWidth = 2.dp
                )
            }
            Text(text = if (uiState.isSaving) "Guardant..." else "Guardar canco")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
