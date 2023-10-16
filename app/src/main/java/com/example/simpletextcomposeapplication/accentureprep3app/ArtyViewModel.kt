package com.example.simpletextcomposeapplication.accentureprep3app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletextcomposeapplication.accentureprep3app.data.repository.ArtyRepository
import com.example.simpletextcomposeapplication.accentureprep3app.domain.ArtyItem
import com.example.simpletextcomposeapplication.accentureprep3app.domain.ArtyItemDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.net.UnknownHostException
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

    private val _detailsState = MutableStateFlow<UiState<ArtyItemDetails, ConnectionError>>(UiState.Starting)
    val detailsState = _detailsState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) { repo.updateArtyList() }
    }

    private var detailsJob: Job? = null
    fun getDetails(id: Long) {
        detailsJob?.cancel()
        detailsJob = viewModelScope.launch(Dispatchers.IO) {
            loadingDetails()
            try {
                withTimeout(1000L) {
                    val response = repo.getArtyDetails(id)
                    _detailsState.update { UiState.Success(response) }
                }
            } catch (e: UnknownHostException) {
                _detailsState.update { UiState.Error(ConnectionError.NoNetwork()) }
            } catch (e: TimeoutCancellationException) {
                _detailsState.update { UiState.Error(ConnectionError.Timeout()) }
            } catch (e: Exception) {
                _detailsState.update { UiState.Error(ConnectionError.Other()) }
                e.printStackTrace()
            }
        }
    }

    private suspend fun loadingDetails() {
        withContext(Dispatchers.Main) {
            _detailsState.update { UiState.Loading }
        }
    }
}

sealed class ConnectionError(val msg: String) {
    data class NoNetwork(val message: String = "No network connection") : ConnectionError(message)
    data class Timeout(val message: String = "Connection timeout") : ConnectionError(message)
    data class Other(val message: String = "An error has occurred") : ConnectionError(message)
}