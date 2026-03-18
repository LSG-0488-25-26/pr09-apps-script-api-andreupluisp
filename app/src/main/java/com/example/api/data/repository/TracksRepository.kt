package com.example.api.data.repository

import com.example.api.BuildConfig
import com.example.api.data.model.AddTrackRequest
import com.example.api.data.model.Track
import com.example.api.data.remote.ApiClient

class TracksRepository {

    suspend fun getTracks(limit: Int = 5000): Result<List<Track>> {
        return runCatching {
            val response = ApiClient.service.getTracks(limit = limit)

            if (!response.ok) {
                error(response.error ?: "No s'han pogut carregar les dades")
            }

            response.items
        }
    }

    suspend fun addTrack(
        trackName: String,
        trackArtist: String,
        playlistGenre: String,
        trackAlbumReleaseDate: String,
        trackAlbumName: String,
        trackPopularity: String,
        uri: String
    ): Result<String> {
        return runCatching {
            val request = AddTrackRequest(
                action = "add",
                apiKey = BuildConfig.API_KEY,
                track_name = trackName,
                track_artist = trackArtist,
                playlist_genre = playlistGenre,
                track_album_release_date = trackAlbumReleaseDate,
                track_album_name = trackAlbumName,
                track_popularity = trackPopularity,
                uri = uri
            )

            val response = ApiClient.service.addTrack(request = request)

            if (!response.ok) {
                error(response.error ?: "No s'ha pogut afegir la canco")
            }

            response.message ?: "Canco afegida"
        }
    }
}
