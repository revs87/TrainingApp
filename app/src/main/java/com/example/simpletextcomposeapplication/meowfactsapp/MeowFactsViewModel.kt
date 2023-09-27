package com.example.simpletextcomposeapplication.meowfactsapp

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.MeowFactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MeowFactsViewModel: ViewModel() {
    val state = mutableStateListOf<String>()
    private lateinit var repo: MeowFactsRepository

    fun init(current: Context) {
        this.repo = MeowFactsRepository(context = current)
        viewModelScope.launch(Dispatchers.IO) {
            val list = repo.getAllMeowFacts()
            withContext(Dispatchers.Main) {
                state.addAll(list)
            }
        }
    }

    fun addMeowFact(count: Int = 3) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = repo.getMoreMeowFacts(count)
            withContext(Dispatchers.Main) {
                state.addAll(0, list.reversed())
            }
        }
    }

}