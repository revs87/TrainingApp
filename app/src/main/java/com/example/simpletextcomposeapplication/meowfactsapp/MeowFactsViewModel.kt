package com.example.simpletextcomposeapplication.meowfactsapp

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletextcomposeapplication.DefaultDispatchers
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.MeowFactsRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MeowFactsViewModel: ViewModel() {
    val state = mutableStateListOf<String>()
    private lateinit var repo: MeowFactsRepository

    fun init(current: Context) {
        this.repo = MeowFactsRepository(context = current)
        viewModelScope.launch(DefaultDispatchers.IO) {
            val list = repo.getAllMeowFacts()
            withContext(DefaultDispatchers.MAIN) {
                state.addAll(list.reversed())
            }
        }
    }

    fun addMeowFact(count: Int = 1) {
        viewModelScope.launch(DefaultDispatchers.IO) {
            val list = repo.getMoreMeowFacts(count)
            withContext(DefaultDispatchers.MAIN) {
                state.addAll(0, list.reversed())
            }
        }
    }

}