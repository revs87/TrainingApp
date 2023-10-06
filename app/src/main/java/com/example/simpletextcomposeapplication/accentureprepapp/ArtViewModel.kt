package com.example.simpletextcomposeapplication.accentureprepapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletextcomposeapplication.accentureprepapp.data.remote.ArtService
import com.example.simpletextcomposeapplication.accentureprepapp.domain.ArtItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
                    response?.data?.map {
                        ArtItem(
                            it.id,
                            it.title,
                        )
                    } ?: emptyList()
                }
            }
        }
    }

}