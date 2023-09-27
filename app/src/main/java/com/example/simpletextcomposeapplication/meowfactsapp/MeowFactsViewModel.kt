package com.example.simpletextcomposeapplication.meowfactsapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletextcomposeapplication.meowfactsapp.data.remote.MeowFactsService
import kotlinx.coroutines.launch

class MeowFactsViewModel: ViewModel() {
    val state = mutableStateListOf<String>()

    private val service = MeowFactsService()

    fun addMeowFact(count: Int = 3) {
        viewModelScope.launch {
            val response = service.getMeowFacts(count)
            state.addAll(0, response.list.reversed())
        }
    }

}