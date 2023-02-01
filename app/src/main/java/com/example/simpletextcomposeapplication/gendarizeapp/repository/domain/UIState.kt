package com.example.simpletextcomposeapplication.gendarizeapp.repository.api

import com.example.simpletextcomposeapplication.gendarizeapp.repository.domain.GenderProfile

sealed class UiState {
    object Loading : UiState()
    data class Success(val gProfile: GenderProfile) : UiState()
    data class Error(val message: String) : UiState()
}