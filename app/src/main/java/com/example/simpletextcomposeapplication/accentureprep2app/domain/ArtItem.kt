package com.example.simpletextcomposeapplication.accentureprep2app.domain

data class ArtItem(
    val id: Long,
    val title: String,
)

data class ArtDetails(
    val id: Long,
    val title: String,
    val description: String? = null,
    val imageId: String? = null,
)