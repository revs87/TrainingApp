package com.example.simpletextcomposeapplication.itunestop100.presentation

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletextcomposeapplication.core.UserPreference
import com.example.simpletextcomposeapplication.itunestop100.domain.repository.ITunesAlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ITunesAlbumsViewModel @Inject constructor(
    private val repository: ITunesAlbumsRepository,
    private val dataStore: DataStore<UserPreference>
) : ViewModel() {

    var hasLoaded = false
    val dataStoreDataState = dataStore.data
        .onStart {
            if (!hasLoaded) {
                refresh()
                hasLoaded = true
            }
        }
        .onEach { println("dataStoreDataState: $it") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = null
        )

    fun getTop100Albums() = repository.getTop100Albums()

    fun refresh() {
        repository.refresh()
        viewModelScope.launch(Dispatchers.IO) {
            val instant = Instant.ofEpochMilli(System.currentTimeMillis())
            val zonedDateTime = instant.atZone(ZoneOffset.UTC)
            val localDateTime = zonedDateTime.toLocalDateTime()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            dataStore.updateData { UserPreference(data = localDateTime.format(formatter)) }
        }
    }
}