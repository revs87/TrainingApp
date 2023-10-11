package com.example.simpletextcomposeapplication.accentureprep2app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletextcomposeapplication.accentureprep2app.domain.ArtDetails
import com.example.simpletextcomposeapplication.accentureprep2app.domain.ArtItem
import com.example.simpletextcomposeapplication.accentureprep2app.repository.ArtRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ArtViewModel @Inject constructor(
    private val repository: ArtRepository
) : ViewModel() {

    val stateListFromFlow: StateFlow<List<ArtItem>> =
        repository
            .getSavedArtItems()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private var detailsId = 0L
    private val _stateDetails = MutableStateFlow<ArtDetails>(ArtDetails(0, ""))
    val stateDetails = _stateDetails.asStateFlow()

    //val state = combine(stateList, _stateDetails) { list: List<ArtItem>, details: ArtDetails -> }

    init {
        getArtList()
    }

    fun getArtList() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getArtList()
            repository.saveArtItems(result)
        }
    }

    fun setId(id: Long?) = id?.let {
        detailsId = it

    }

}