package com.example.simpletextcomposeapplication.accentureprep4app.data.api

import com.google.gson.annotations.SerializedName

data class ArtiListResponse(
    @SerializedName("data") val data: List<ArtiListItemResponse>
)

data class ArtiListItemResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
)

data class ArtItemResponse(
    @SerializedName("data") val data: ArtItemDetailsResponse
)

data class ArtItemDetailsResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("image_id") val imageId: String?,
)
