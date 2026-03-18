package com.example.api.data.model

import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("track_name")
    val trackName: String = "",
    @SerializedName("track_artist")
    val trackArtist: String = "",
    @SerializedName("playlist_genre")
    val playlistGenre: String = "",
    @SerializedName("track_album_release_date")
    val trackAlbumReleaseDate: String = "",
    @SerializedName("track_album_name")
    val trackAlbumName: String = "",
    @SerializedName("track_id")
    val trackId: String = "",
    @SerializedName("track_popularity")
    val trackPopularity: String = "",
    @SerializedName("uri")
    val uri: String = ""
)
