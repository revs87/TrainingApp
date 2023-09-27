package com.example.simpletextcomposeapplication.meowfactsapp.data.remote

import com.google.gson.annotations.SerializedName

data class MeowFactsResponse(@SerializedName("data") val list: List<String>)