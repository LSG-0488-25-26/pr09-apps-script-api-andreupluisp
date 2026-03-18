package com.example.api.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.data.repository.TracksRepository
import com.example.api.ui.state.AddTrackUiState
import com.example.api.util.isValidIsoDate
import kotlinx.coroutines.launch

class AddTrackViewModel : ViewModel() {

    private val repository = TracksRepository()

    private val _uiState = MutableLiveData(AddTrackUiState())
    val uiState: LiveData<AddTrackUiState> = _uiState

    fun onTrackNameChange(value: String) {
        updateState { copy(trackName = value, errorMessage = null, successMessage = null) }
    }

    fun onTrackArtistChange(value: String) {
        updateState { copy(trackArtist = value, errorMessage = null, successMessage = null) }
    }

    fun onPlaylistGenreChange(value: String) {
        updateState { copy(playlistGenre = value, errorMessage = null, successMessage = null) }
    }

    fun onTrackAlbumReleaseDateChange(value: String) {
        updateState { copy(trackAlbumReleaseDate = value, errorMessage = null, successMessage = null) }
    }

    fun onTrackAlbumNameChange(value: String) {
        updateState { copy(trackAlbumName = value, errorMessage = null, successMessage = null) }
    }

    fun onTrackPopularityChange(value: String) {
        updateState { copy(trackPopularity = value, errorMessage = null, successMessage = null) }
    }

    fun onUriChange(value: String) {
        updateState { copy(uri = value, errorMessage = null, successMessage = null) }
    }

    fun saveTrack() {
        val state = _uiState.value ?: return
        val trackName = state.trackName.trim()
        val trackArtist = state.trackArtist.trim()

        when {
            trackName.isEmpty() -> setError("Escriu el nom de la canco")
            trackArtist.isEmpty() -> setError("Escriu l'artista")
            state.trackAlbumReleaseDate.isNotBlank() && !isValidIsoDate(state.trackAlbumReleaseDate.trim()) ->
                setError("La data ha de tenir format YYYY-MM-DD")
            else -> {
                viewModelScope.launch {
                    _uiState.value = state.copy(
                        isSaving = true,
                        errorMessage = null,
                        successMessage = null
                    )

                    repository.addTrack(
                        trackName = trackName,
                        trackArtist = trackArtist,
                        playlistGenre = state.playlistGenre.trim(),
                        trackAlbumReleaseDate = state.trackAlbumReleaseDate.trim(),
                        trackAlbumName = state.trackAlbumName.trim(),
                        trackPopularity = state.trackPopularity.trim(),
                        uri = state.uri.trim()
                    ).fold(
                        onSuccess = { message ->
                            _uiState.value = AddTrackUiState(
                                successMessage = message
                            )
                        },
                        onFailure = { throwable ->
                            _uiState.value = state.copy(
                                isSaving = false,
                                errorMessage = throwable.message ?: "Error afegint la canco",
                                successMessage = null
                            )
                        }
                    )
                }
            }
        }
    }

    fun clearMessages() {
        updateState { copy(errorMessage = null, successMessage = null) }
    }

    private fun setError(message: String) {
        updateState {
            copy(
                errorMessage = message,
                successMessage = null
            )
        }
    }

    private fun updateState(transform: AddTrackUiState.() -> AddTrackUiState) {
        val currentState = _uiState.value ?: AddTrackUiState()
        _uiState.value = currentState.transform()
    }
}
