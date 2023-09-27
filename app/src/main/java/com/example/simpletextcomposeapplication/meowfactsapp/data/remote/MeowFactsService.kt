package com.example.simpletextcomposeapplication.meowfactsapp.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MeowFactsService(
    private val api: MeowFactsApi = createApi()
) {
    suspend fun getMeowFacts(count: Int): MeowFactsResponse = api.getMeowFacts(count)
}

fun createApi(): MeowFactsApi {
    val okHttpClient = OkHttpClient.Builder().build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://meowfacts.herokuapp.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(MeowFactsApi::class.java)
}