package com.example.api.data.model

data class AddTrackRequest(
    val action: String,
    val apiKey: String,
    val track_name: String,
    val track_artist: String,
    val playlist_genre: String,
    val track_album_release_date: String,
    val track_album_name: String,
    val track_popularity: String,
    val uri: String
)
