package com.example.api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api.ui.screen.AddTrackScreen
import com.example.api.ui.screen.AuthScreen
import com.example.api.ui.screen.TracksScreen
import com.example.api.ui.theme.ApiTheme
import com.example.api.ui.viewmodel.AddTrackViewModel
import com.example.api.ui.viewmodel.AuthViewModel
import com.example.api.ui.viewmodel.TracksViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApiTheme {
                SpotifyApp()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SpotifyApp(
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory),
    addTrackViewModel: AddTrackViewModel = viewModel(),
    tracksViewModel: TracksViewModel = viewModel()
) {
    val authState by authViewModel.uiState.observeAsState()
    val addTrackState by addTrackViewModel.uiState.observeAsState()
    val tracks by tracksViewModel.tracks.observeAsState(emptyList())
    val isLoading by tracksViewModel.isLoading.observeAsState(false)
    val tracksError by tracksViewModel.error.observeAsState()
    var homeSection by rememberSaveable { mutableStateOf(HomeSection.LIST.name) }

    val currentState = authState ?: return
    val currentAddTrackState = addTrackState ?: return

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                title = {
                    Text(
                        text = if (currentState.isLoggedIn) {
                            "Spotify tracks"
                        } else {
                            "Login"
                        }
                    )
                }
            )
        }
    ) { innerPadding ->
        when {
            !currentState.isLoggedIn -> {
                AuthScreen(
                    uiState = currentState,
                    modifier = Modifier.padding(innerPadding),
                    onUsernameChange = authViewModel::onUsernameChange,
                    onPasswordChange = authViewModel::onPasswordChange,
                    onConfirmPasswordChange = authViewModel::onConfirmPasswordChange,
                    onLoginClick = authViewModel::login,
                    onRegisterClick = authViewModel::register,
                    onShowLoginClick = authViewModel::showLogin,
                    onShowRegisterClick = authViewModel::showRegister
                )
            }

            BuildConfig.BASE_URL.isBlank() || BuildConfig.API_KEY.isBlank() -> {
                MissingSecrets(modifier = Modifier.padding(innerPadding))
            }

            else -> {
                when (HomeSection.valueOf(homeSection)) {
                    HomeSection.LIST -> {
                        TracksScreen(
                            username = currentState.currentUser,
                            tracks = tracks,
                            isLoading = isLoading,
                            error = tracksError,
                            modifier = Modifier.padding(innerPadding),
                            onOpenAddTrack = {
                                addTrackViewModel.clearMessages()
                                homeSection = HomeSection.ADD.name
                            },
                            onReload = tracksViewModel::loadTracks,
                            onLogout = {
                                homeSection = HomeSection.LIST.name
                                authViewModel.logout()
                            }
                        )
                    }

                    HomeSection.ADD -> {
                        AddTrackScreen(
                            username = currentState.currentUser,
                            uiState = currentAddTrackState,
                            modifier = Modifier.padding(innerPadding),
                            onTrackNameChange = addTrackViewModel::onTrackNameChange,
                            onTrackArtistChange = addTrackViewModel::onTrackArtistChange,
                            onPlaylistGenreChange = addTrackViewModel::onPlaylistGenreChange,
                            onTrackAlbumReleaseDateChange = addTrackViewModel::onTrackAlbumReleaseDateChange,
                            onTrackAlbumNameChange = addTrackViewModel::onTrackAlbumNameChange,
                            onTrackPopularityChange = addTrackViewModel::onTrackPopularityChange,
                            onUriChange = addTrackViewModel::onUriChange,
                            onSaveClick = addTrackViewModel::saveTrack,
                            onBackToListClick = {
                                addTrackViewModel.clearMessages()
                                tracksViewModel.loadTracks()
                                homeSection = HomeSection.LIST.name
                            },
                            onLogoutClick = {
                                homeSection = HomeSection.LIST.name
                                authViewModel.logout()
                            }
                        )
                    }
                }
            }
        }
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

private enum class HomeSection {
    LIST,
    ADD
}
