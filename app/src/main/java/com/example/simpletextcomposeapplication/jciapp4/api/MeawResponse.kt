package com.example.simpletextcomposeapplication.jciapp4.api

import com.google.gson.annotations.SerializedName

data class MeawResponse(
    @SerializedName("data") val data: List<String>
)