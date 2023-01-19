package com.example.simpletextcomposeapplication.ui.meals

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletextcomposeapplication.model.MealsRepository
import com.example.simpletextcomposeapplication.model.response.CategoryResponse
import kotlinx.coroutines.launch

class MealsCategoryDetailsViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val repository: MealsRepository = MealsRepository.getInstance()

    val categoryState = mutableStateOf<CategoryResponse?>(null)

    init {
        val categoryId = savedStateHandle.get<String>("category_id") ?: ""
        viewModelScope.launch { categoryState.value = getCategory(categoryId) }
    }

    private suspend fun getCategory(id: String): CategoryResponse? = repository.getCategory(id)
}