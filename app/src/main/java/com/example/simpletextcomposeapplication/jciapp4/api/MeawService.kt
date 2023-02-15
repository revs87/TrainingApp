package com.example.simpletextcomposeapplication.jciapp4.api

import com.example.simpletextcomposeapplication.jciapp4.MeawData
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface MeawApi {

    @GET("/")
    suspend fun getData(@Query("count") count: String): MeawResponse

}


fun createApi(): MeawApi {
    val okHttpClient = OkHttpClient.Builder()
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://meowfacts.herokuapp.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(MeawApi::class.java)
}

class MeawService {
    private val api: MeawApi = createApi()

    suspend fun getData(numberOfLines: Int): List<MeawData> = api.getData(numberOfLines.toString()).toDomain()
}

private fun MeawResponse.toDomain(): List<MeawData> = this.data.map { MeawData(it) }
