package com.example.simpletextcomposeapplication.itunestop100.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletextcomposeapplication.itunestop100.domain.repository.ITunesAlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ITunesAlbumsViewModel @Inject constructor(
    private val repository: ITunesAlbumsRepository
) : ViewModel() {

    init { repository.refresh() }

    fun getTop100Albums() = repository.getTop100Albums()

    fun refresh() = repository.refresh()
}