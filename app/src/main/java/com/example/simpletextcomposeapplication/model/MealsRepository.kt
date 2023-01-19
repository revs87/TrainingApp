package com.example.simpletextcomposeapplication.model

import com.example.simpletextcomposeapplication.model.api.CategoriesWebService
import com.example.simpletextcomposeapplication.model.response.CategoriesResponse
import com.example.simpletextcomposeapplication.model.response.CategoryResponse

class MealsRepository(private val webService: CategoriesWebService = CategoriesWebService()) {
    private var cached: List<CategoryResponse>? = null
    suspend fun getCategories(): CategoriesResponse {
        val response = webService.getCategories()
        cached = response.categories
        return response
    }
    suspend fun getCategory(id: String): CategoryResponse? = cached?.category(id) ?: getCategories().categories.category(id)

    companion object {
        /* Repository pattern (Singleton) */
        @Volatile
        private var instance: MealsRepository? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: MealsRepository().also { instance = it }
        }
    }
}

private fun List<CategoryResponse>.category(id: String) = this.firstOrNull() { it.id == id }
