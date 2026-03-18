package com.example.api.data.remote

import com.example.api.BuildConfig
import com.example.api.data.model.TracksResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface SpotifyApiService {

    @GET
    suspend fun getTracks(
        @Url url: String = BuildConfig.BASE_URL,
        @Query("action") action: String = "list",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("limit") limit: Int = 20
    ): TracksResponse
}
