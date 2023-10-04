package com.example.simpletextcomposeapplication.meowfactsapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletextcomposeapplication.DefaultDispatchers
import com.example.simpletextcomposeapplication.DispatcherProvider
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.MeowFactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MeowFactsViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val repo: MeowFactsRepository
) : ViewModel() {
    val state = mutableStateListOf<String>()

    fun init() {
        viewModelScope.launch(dispatcherProvider.io) {
            val list = repo.getAllMeowFacts()
            withContext(DefaultDispatchers.MAIN) {
                state.clear()
                state.addAll(list.reversed())
            }
        }
    }

    fun addMeowFact(count: Int = 1) {
        viewModelScope.launch(dispatcherProvider.io) {
            val list = repo.getMoreMeowFacts(count)
            withContext(DefaultDispatchers.MAIN) {
                state.addAll(0, list.takeLast(count).reversed())
            }
        }
    }

}