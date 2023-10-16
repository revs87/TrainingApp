package com.example.simpletextcomposeapplication.accentureprep3app

import java.sql.Timestamp


sealed class UiState<out R, out E> {
    data object Starting : UiState<Nothing, Nothing>()
    data object Loading : UiState<Nothing, Nothing>()
    data class Success<R>(val result: R) : UiState<R, Nothing>()
    data class Error<E>(val error: E, val timestamp: Timestamp = Timestamp(System.currentTimeMillis())) : UiState<Nothing, E>()
}