package com.example.simpletextcomposeapplication.accentureapp.data.remote

import javax.inject.Inject


interface ArtService {
    suspend fun getArtItems(): ArtItemsResponse
}

class ArtServiceImpl @Inject constructor(
    private val api: ArtApi
) : ArtService {
    override suspend fun getArtItems(): ArtItemsResponse = api.getArtItems(
        "id,title,artist_display,date_display,main_reference_number",
        100
    )
}