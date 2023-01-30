package com.example.simpletextcomposeapplication.repository

import com.example.simpletextcomposeapplication.domain.CategoryDomain
import com.example.simpletextcomposeapplication.domain.response.CategoriesResponse
import com.example.simpletextcomposeapplication.domain.response.CategoryResponse
import com.example.simpletextcomposeapplication.repository.api.CategoriesWebService

class MealsRepository(private val webService: CategoriesWebService = CategoriesWebService()) : Repository<MealsRepository>() {
    private var cached: List<CategoryResponse>? = null
    suspend fun getCategories(): CategoriesResponse {
        val response = webService.getCategories()
        cached = response.categories
        return response
    }
    suspend fun getCategory(id: String): CategoryDomain? = cached?.toDomain()?.category(id) ?: getCategories().categories.toDomain().category(id)
}

private fun List<CategoryResponse>.toDomain(): List<CategoryDomain> = this.map { CategoryDomain(it.name, it.description, it.imageUrl) }.toList()
private fun List<CategoryDomain>.category(id: String) = this.firstOrNull() { it.id == id }
