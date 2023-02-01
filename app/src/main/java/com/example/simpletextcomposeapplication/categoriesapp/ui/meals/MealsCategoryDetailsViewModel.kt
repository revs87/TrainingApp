package com.example.simpletextcomposeapplication.categoriesapp.ui.meals

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletextcomposeapplication.categoriesapp.repository.domain.CategoryDomain
import com.example.simpletextcomposeapplication.categoriesapp.repository.CategoryRepository
import kotlinx.coroutines.launch

class MealsCategoryDetailsViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val repository: CategoryRepository = CategoryRepository.getInstance()

    val categoryState = mutableStateOf<CategoryDomain?>(null)

    init {
        val categoryId = savedStateHandle.get<String>("category_id") ?: ""
        viewModelScope.launch { categoryState.value = getCategory(categoryId) }
    }

    private suspend fun getCategory(id: String): CategoryDomain? = repository.getCategory(id)
}