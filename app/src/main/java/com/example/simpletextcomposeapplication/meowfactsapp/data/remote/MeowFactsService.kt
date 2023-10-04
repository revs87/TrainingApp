package com.example.simpletextcomposeapplication.meowfactsapp.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface MeowFactsService {
    suspend fun getMeowFacts(count: Int): MeowFactsResponse
}

class MeowFactsServiceImpl(
    private val api: MeowFactsApi
) : MeowFactsService {
    override suspend fun getMeowFacts(count: Int): MeowFactsResponse = api.getMeowFacts(count)
}

private fun createApi(): MeowFactsApi {
    val okHttpClient = OkHttpClient.Builder().build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://meowfacts.herokuapp.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(MeowFactsApi::class.java)
}