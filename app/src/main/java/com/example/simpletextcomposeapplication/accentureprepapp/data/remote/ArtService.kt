package com.example.simpletextcomposeapplication.accentureprepapp.data.remote

import javax.inject.Inject


interface ArtService {
    suspend fun getArtItems(): ArtItemsResponse

    suspend fun getArtItemDetails(id: Long): ArtItemDetailsResponse
}

class ArtServiceImpl @Inject constructor(
    private val api: ArtApi
) : ArtService {
    override suspend fun getArtItems(): ArtItemsResponse = api.getArtItems(
        "id,title,artist_display,date_display,main_reference_number",
        100
    )

    override suspend fun getArtItemDetails(id: Long): ArtItemDetailsResponse = api.getArtItemDetails(id)
}