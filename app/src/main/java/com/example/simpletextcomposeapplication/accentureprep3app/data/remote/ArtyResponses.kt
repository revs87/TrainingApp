package com.example.simpletextcomposeapplication.accentureprep3app.data.remote

import com.google.gson.annotations.SerializedName

data class ArtyListResponse(
    @SerializedName("data") val data: List<ArtyResponse>
)
data class ArtyResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
)

data class ArtyDetailsResponse(
    @SerializedName("data") val data: ArtyDetailedResponse
)
data class ArtyDetailedResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("image_id") val imageId: String?,
)