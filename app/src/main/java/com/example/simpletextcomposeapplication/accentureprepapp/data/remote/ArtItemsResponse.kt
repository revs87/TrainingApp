package com.example.simpletextcomposeapplication.accentureprepapp.data.remote

import com.google.gson.annotations.SerializedName


data class ArtItemsResponse(
    @SerializedName("data") val data: List<ArtItemResponse>,
)

data class ArtItemResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("main_reference_number") val reference: String,
    @SerializedName("date_display") val date: String,
    @SerializedName("artist_display") val artist: String,
)

data class ArtItemDetailsResponse(
    @SerializedName("data") val details: ArtItemDetailsObjResponse
)

data class ArtItemDetailsObjResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("image_id") val imageId: String?,
)