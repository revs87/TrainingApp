package com.example.simpletextcomposeapplication.model

import com.example.simpletextcomposeapplication.model.api.MealsWebService
import com.example.simpletextcomposeapplication.model.response.CategoriesResponse

class MealsRepository(private val webService: MealsWebService = MealsWebService()) {
    suspend fun getMeals(): CategoriesResponse = webService.getMeals()
}