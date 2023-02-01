package com.example.simpletextcomposeapplication.gendarizeapp.repository.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GenderizeApi {

    /* https://api.genderize.io/?name=Simone&country_id=IT */

    @GET("/")
    suspend fun getGender(@Query("name") name: String): GenderProfileResponse

    @GET("/")
    suspend fun getGenderByCountry(@Query("name") name: String, @Query("country_id") countryId: String): GenderProfileByCountryResponse
}

fun createApi(): GenderizeApi {
    val okHttpClient = OkHttpClient.Builder()
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.genderize.io/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(GenderizeApi::class.java)
}

class GenderizeService() {
    private val api: GenderizeApi = createApi()

    suspend fun getGender(name: String): GenderProfileResponse = api.getGender(name)
    suspend fun getGenderByCountry(name: String, countryId: String): GenderProfileByCountryResponse = api.getGenderByCountry(name, countryId)
}