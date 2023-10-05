package com.example.simpletextcomposeapplication.meowfactsapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletextcomposeapplication.DefaultDispatchers
import com.example.simpletextcomposeapplication.DispatcherProvider
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.MeowFactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MeowFactsViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val repo: MeowFactsRepository
) : ViewModel() {
    val stateList = mutableStateListOf<String>()

    private val _listStateFlow = MutableStateFlow<List<String>>(emptyList())
    val listStateFlow = _listStateFlow.asStateFlow()

    fun init() {
        viewModelScope.launch(dispatcherProvider.io) {
            val list = repo.getAllMeowFacts()
            withContext(DefaultDispatchers.MAIN) {
                stateList.clear()
                stateList.addAll(list.reversed())
                _listStateFlow.update {
                    list.reversed()
                }
            }
        }
    }

    fun addMeowFact(count: Int = 1) {
        viewModelScope.launch(dispatcherProvider.io) {
            val list = repo.getMoreMeowFacts(count)
            withContext(DefaultDispatchers.MAIN) {
                stateList.addAll(0, list.takeLast(count).reversed())

                _listStateFlow.update {
                    list.takeLast(count).reversed()
                }
            }
        }
    }

}