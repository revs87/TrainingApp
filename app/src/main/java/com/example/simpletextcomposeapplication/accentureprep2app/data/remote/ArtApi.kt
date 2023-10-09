package com.example.simpletextcomposeapplication.accentureprep2app.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ArtApi2 {

    @GET("/api/v1/artworks")
    suspend fun getArtList(
        @Query("fields") fields: String = "id,title,artist_display,date_display,main_reference_number"
    ): ArtDataResponse

}