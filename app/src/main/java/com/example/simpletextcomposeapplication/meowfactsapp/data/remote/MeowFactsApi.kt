package com.example.simpletextcomposeapplication.meowfactsapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MeowFactsApi {

    @GET("/")
    suspend fun getMeowFacts(@Query("count") count: Int): MeowFactsResponse

}