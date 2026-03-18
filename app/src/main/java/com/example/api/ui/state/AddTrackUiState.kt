package com.example.api.ui.state

data class AddTrackUiState(
    val trackName: String = "",
    val trackArtist: String = "",
    val playlistGenre: String = "",
    val trackAlbumReleaseDate: String = "",
    val trackAlbumName: String = "",
    val trackPopularity: String = "",
    val uri: String = "",
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
