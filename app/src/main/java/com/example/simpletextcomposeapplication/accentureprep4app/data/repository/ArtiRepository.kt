package com.example.simpletextcomposeapplication.accentureprep4app.data.repository

import com.example.simpletextcomposeapplication.accentureprep4app.data.api.ArtItemResponse
import com.example.simpletextcomposeapplication.accentureprep4app.data.api.ArtiApi
import com.example.simpletextcomposeapplication.accentureprep4app.data.api.ArtiListResponse
import com.example.simpletextcomposeapplication.accentureprep4app.domain.ArtiItem
import com.example.simpletextcomposeapplication.accentureprep4app.domain.ArtiItemDetails
import javax.inject.Inject

interface ArtiRepository {
    suspend fun getArtiList(): List<ArtiItem>
    suspend fun getArtiDetails(id: Long): ArtiItemDetails
}

class ArtiRepositoryImpl @Inject constructor(
    private val api: ArtiApi
) : ArtiRepository {
    override suspend fun getArtiList(): List<ArtiItem> = api.getArtiList("id,title,artist_display,date_display,main_reference_number").toDomain()
    override suspend fun getArtiDetails(id: Long): ArtiItemDetails = api.getArtiDetails(id).toDomain()

    private fun ArtiListResponse.toDomain(): List<ArtiItem> = this.data.map { ArtiItem(it.id, it.title) }
    private fun ArtItemResponse.toDomain(): ArtiItemDetails = ArtiItemDetails(this.data.id, this.data.title, this.data.description, this.data.imageId)
}