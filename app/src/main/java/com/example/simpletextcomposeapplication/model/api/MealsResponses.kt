package com.example.simpletextcomposeapplication.model.response

import com.google.gson.annotations.SerializedName

/* https://www.themealdb.com/api/json/v1/1/categories.php */

data class CategoriesResponse(val categories: List<CategoryResponse>)

data class CategoryResponse(
    @SerializedName("idCategory") val id: String,
    @SerializedName("strCategory") val name: String,
    @SerializedName("strCategoryDescription") val description: String,
    @SerializedName("strCategoryThumb") val imageUrl: String
)