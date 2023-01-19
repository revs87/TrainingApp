package com.example.simpletextcomposeapplication.ui.meals

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletextcomposeapplication.model.MealsRepository
import com.example.simpletextcomposeapplication.model.response.CategoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealsCategoriesViewModel(private val repository: MealsRepository = MealsRepository.getInstance()) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) { mealsState.value = getCategories() }
    }

    val mealsState: MutableState<List<CategoryResponse>> = mutableStateOf(emptyList())

    private suspend fun getCategories(): List<CategoryResponse> = repository.getCategories().categories
}
