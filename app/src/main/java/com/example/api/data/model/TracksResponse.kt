package com.example.api.data.model

data class TracksResponse(
    val ok: Boolean = false,
    val total: Int = 0,
    val items: List<Track> = emptyList(),
    val error: String? = null
)
