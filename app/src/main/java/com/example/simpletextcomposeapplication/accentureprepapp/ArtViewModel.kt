package com.example.simpletextcomposeapplication.accentureprepapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletextcomposeapplication.accentureprepapp.data.remote.ArtService
import com.example.simpletextcomposeapplication.accentureprepapp.domain.ArtDetailsItem
import com.example.simpletextcomposeapplication.accentureprepapp.domain.ArtItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ArtViewModel @Inject constructor(
    private val service: ArtService
) : ViewModel() {

    private val _state = MutableStateFlow<List<ArtItem>>(emptyList())
    val state = _state.asStateFlow()

    init {
        getArtItems()
    }

    private fun getArtItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = service.getArtItems()
            withContext(Dispatchers.Main) {
                _state.update {
                    response.data.map {
                        ArtItem(
                            it.id,
                            it.title,
                        )
                    }
                }
            }
        }
    }

    private var refreshJob: Job? = null
    fun refreshArtItems() {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                _state.update { emptyList() }
            }
            getArtItems()
        }
    }


    private val _stateArtDetails = MutableStateFlow<ArtDetailsItem>(ArtDetailsItem(0L, "", "", ""))
    val stateArtDetails = _stateArtDetails.asStateFlow()

    private var detailsJob: Job? = null
    fun getArtItemDetails(artId: Long) {
        detailsJob?.cancel()
        detailsJob = viewModelScope.launch(Dispatchers.IO) {
            val response = service.getArtItemDetails(artId)
            withContext(Dispatchers.Main) {
                _stateArtDetails.update {
                    ArtDetailsItem(
                        response.details.id,
                        response.details.title ?: "",
                        response.details.description ?: "",
                        response.details.imageId ?: "",
                    )
                }
            }
        }
    }

}