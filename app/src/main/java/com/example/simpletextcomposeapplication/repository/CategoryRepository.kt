package com.example.simpletextcomposeapplication.repository

import com.example.simpletextcomposeapplication.domain.CategoryDomain
import com.example.simpletextcomposeapplication.domain.response.CategoryResponse
import com.example.simpletextcomposeapplication.repository.api.CategoriesWebService

class CategoryRepository(private val webService: CategoriesWebService = CategoriesWebService()) {
    private var cachedCategories: List<CategoryDomain>? = null
    suspend fun getCategories(): List<CategoryDomain> {
        val response = webService.getCategories().categories.toDomain()
        cachedCategories = response
        return response
    }
    suspend fun getCategory(id: String): CategoryDomain? = cachedCategories?.category(id) ?: getCategories().category(id)
    companion object {
        /* Repository pattern (Singleton) */
        @Volatile
        private var instance: CategoryRepository? = null

        fun getInstance(): CategoryRepository = instance ?: synchronized(this) {
            instance ?: CategoryRepository().also { instance = it }
        }
    }
}

private fun List<CategoryResponse>.toDomain(): List<CategoryDomain> = this.map { CategoryDomain(it.id, it.name, it.description, it.imageUrl) }.toList()
private fun List<CategoryDomain>.category(id: String) = this.firstOrNull() { it.id == id }
