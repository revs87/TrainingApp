package com.example.simpletextcomposeapplication.accentureprep4app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletextcomposeapplication.accentureprep4app.data.repository.ArtiRepository
import com.example.simpletextcomposeapplication.accentureprep4app.domain.ArtiItem
import com.example.simpletextcomposeapplication.accentureprep4app.domain.ArtiItemDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtiViewModel @Inject constructor(
    private val repo: ArtiRepository
) : ViewModel() {

    private val _listState = MutableStateFlow<List<ArtiItem>>(emptyList())
    val listState = _listState.asStateFlow()

    private val _detailsState = MutableStateFlow<ArtiItemDetails>(ArtiItemDetails(0L, "", "", ""))
    val detailsState = _detailsState.asStateFlow()


    private var listJob: Job? = null
    private var detailsJob: Job? = null

    init {
        listJob?.cancel()
        listJob = viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getArtiList()
            _listState.update { result }
        }
    }

    fun getDetails(id: Long) {
        detailsJob?.cancel()
        detailsJob = viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getArtiDetails(id)
            _detailsState.update { result }
        }
    }
}