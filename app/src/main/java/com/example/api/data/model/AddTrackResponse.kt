package com.example.api.data.model

data class AddTrackResponse(
    val ok: Boolean = false,
    val message: String? = null,
    val item: Track? = null,
    val error: String? = null
)
