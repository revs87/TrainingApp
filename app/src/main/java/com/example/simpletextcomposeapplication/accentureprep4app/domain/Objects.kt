package com.example.simpletextcomposeapplication.accentureprep4app.domain

data class ArtiItem(
    val id: Long,
    val title: String
)

data class ArtiItemDetails(
    val id: Long,
    val title: String,
    val description: String?,
    val imageId: String?,
)