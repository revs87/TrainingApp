package com.example.simpletextcomposeapplication.gendarizeapp.repository.api

import com.google.gson.annotations.SerializedName

data class GenderProfileResponse(
    @SerializedName("count") val count: String,
    @SerializedName("country_id") val countryId: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("name") val name: String,
    @SerializedName("probability") val probability: Double
)

data class GenderProfileByCountryResponse(
    @SerializedName("count") val count: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("name") val name: String,
    @SerializedName("probability") val probability: Double
)