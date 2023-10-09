package com.example.simpletextcomposeapplication.accentureprep2app.data.remote

import com.google.gson.annotations.SerializedName

data class ArtDataResponse(
    @SerializedName("data") val data: List<ArtItemResponse>
)

data class ArtItemResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String? = null,
    @SerializedName("image_id") val imageId: String? = null,
)
