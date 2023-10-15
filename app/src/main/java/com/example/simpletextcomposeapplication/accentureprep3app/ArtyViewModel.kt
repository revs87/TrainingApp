package com.example.simpletextcomposeapplication.accentureprep3app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletextcomposeapplication.accentureprep3app.data.repository.ArtyRepository
import com.example.simpletextcomposeapplication.accentureprep3app.domain.ArtyItem
import com.example.simpletextcomposeapplication.accentureprep3app.domain.ArtyItemDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArtyViewModel @Inject constructor(
    private val repo: ArtyRepository
) : ViewModel() {

    val listState: StateFlow<List<ArtyItem>> =
        repo.getArtyList()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                emptyList()
            )

    private val _detailsState = MutableStateFlow<ArtyItemDetails>(ArtyItemDetails(0L, "", "", ""))
    val detailsState = _detailsState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) { repo.updateArtyList() }
    }

    private var detailsJob: Job? = null
    fun getDetails(id: Long) {
        detailsJob?.cancel()
        detailsJob = viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getArtyDetails(id)
            withContext(Dispatchers.Main) {
                _detailsState.update { response }
            }
        }
    }

}