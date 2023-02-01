package com.example.simpletextcomposeapplication.categoriesapp.repository.api

import com.example.simpletextcomposeapplication.domain.response.CategoriesResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class CategoriesWebService(
    baseUrl: String = "https://www.themealdb.com/api/json/v1/1/",
    converterFactory: GsonConverterFactory = GsonConverterFactory.create()
) {
    private val api: CategoriesApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .build()

        api = retrofit.create(CategoriesApi::class.java)
    }

    interface CategoriesApi {
        @GET("categories.php")
        suspend fun getCategories(): CategoriesResponse
    }

    suspend fun getCategories(): CategoriesResponse = api.getCategories()
}