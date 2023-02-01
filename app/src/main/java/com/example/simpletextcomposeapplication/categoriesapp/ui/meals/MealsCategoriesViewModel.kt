package com.example.simpletextcomposeapplication.categoriesapp.ui.meals

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletextcomposeapplication.categoriesapp.repository.domain.CategoryDomain
import com.example.simpletextcomposeapplication.categoriesapp.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MealsCategoriesViewModel(private val repository: CategoryRepository = CategoryRepository.getInstance()) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) { categoriesState.value = getCategories() }
    }

    val categoriesState: MutableState<List<CategoryDomain>> = mutableStateOf(emptyList())

    private suspend fun getCategories(): List<CategoryDomain> = repository.getCategories()
}
