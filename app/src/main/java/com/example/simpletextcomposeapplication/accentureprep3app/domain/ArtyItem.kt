package com.example.simpletextcomposeapplication.accentureprep3app.domain

data class ArtyItem(
    val id: Long,
    val title: String
)

data class ArtyItemDetails(
    val id: Long,
    val title: String,
    val description: String?,
    val imageId: String?,
)