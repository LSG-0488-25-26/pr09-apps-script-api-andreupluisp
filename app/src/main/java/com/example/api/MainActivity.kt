package com.example.api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api.data.model.Track
import com.example.api.ui.theme.ApiTheme
import com.example.api.ui.viewmodel.TracksViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApiTheme {
                TracksApp()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TracksApp(viewModel: TracksViewModel = viewModel()) {
    val tracks by viewModel.tracks.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Spotify tracks")
                }
            )
        }
    ) { innerPadding ->
        if (BuildConfig.BASE_URL.isBlank() || BuildConfig.API_KEY.isBlank()) {
            MissingSecrets(modifier = Modifier.padding(innerPadding))
            return@Scaffold
        }

        TracksScreen(
            tracks = tracks,
            isLoading = isLoading,
            error = error,
            modifier = Modifier.padding(innerPadding),
            onReload = viewModel::loadTracks
        )
    }
}

@Composable
fun MissingSecrets(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Falta configurar secrets.properties amb BASE_URL i API_KEY",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun TracksScreen(
    tracks: List<Track>,
    isLoading: Boolean,
    error: String?,
    modifier: Modifier = Modifier,
    onReload: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Primeres cancons",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Button(onClick = onReload) {
                Text(text = "Recarrega")
            }
        }

        if (error != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tracks) { track ->
                    TrackCard(track = track)
                }
            }
        }
    }
}

@Composable
fun TrackCard(track: Track) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = track.trackName.ifBlank { "Sense nom" },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = track.trackArtist.ifBlank { "Artista desconegut" },
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Genere: ${track.playlistGenre.ifBlank { "-" }}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Popularitat: ${track.trackPopularity.ifBlank { "-" }}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Album: ${track.trackAlbumName.ifBlank { "-" }}",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Data: ${track.trackAlbumReleaseDate.ifBlank { "-" }}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
