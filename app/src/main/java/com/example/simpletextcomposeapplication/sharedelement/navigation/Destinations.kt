package com.example.simpletextcomposeapplication.sharedelement.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class HomeDetails(
    val resId: Int,
    val text: String
)