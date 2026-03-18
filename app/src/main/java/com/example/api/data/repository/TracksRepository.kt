package com.example.api.data.repository

import com.example.api.data.model.Track
import com.example.api.data.remote.ApiClient

class TracksRepository {

    suspend fun getTracks(limit: Int = 20): Result<List<Track>> {
        return runCatching {
            val response = ApiClient.service.getTracks(limit = limit)

            if (!response.ok) {
                error(response.error ?: "No s'han pogut carregar les dades")
            }

            response.items
        }
    }
}
