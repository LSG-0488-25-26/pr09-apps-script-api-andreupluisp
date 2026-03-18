package com.example.api.data.remote

import com.example.api.BuildConfig
import com.example.api.data.model.AddTrackRequest
import com.example.api.data.model.AddTrackResponse
import com.example.api.data.model.TracksResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @POST
    suspend fun addTrack(
        @Url url: String = BuildConfig.BASE_URL,
        @Body request: AddTrackRequest
    ): AddTrackResponse
}
