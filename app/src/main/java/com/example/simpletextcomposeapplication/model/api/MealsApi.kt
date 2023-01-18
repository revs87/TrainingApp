package com.example.simpletextcomposeapplication.model.api

import com.example.simpletextcomposeapplication.model.response.CategoriesResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class MealsWebService(
    baseUrl: String = "https://www.themealdb.com/api/json/v1/1/",
    converterFactory: GsonConverterFactory = GsonConverterFactory.create()
) {
    private lateinit var api: MealsApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .build()

        api = retrofit.create(MealsApi::class.java)
    }

    interface MealsApi {
        @GET("categories.php")
        suspend fun getMeals(): CategoriesResponse
    }

    suspend fun getMeals(): CategoriesResponse = api.getMeals()
}