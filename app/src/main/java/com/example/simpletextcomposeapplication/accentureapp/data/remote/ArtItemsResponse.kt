package com.example.simpletextcomposeapplication.accentureapp.data.remote

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
