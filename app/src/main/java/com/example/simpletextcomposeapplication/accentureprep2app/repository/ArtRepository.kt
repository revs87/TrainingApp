package com.example.simpletextcomposeapplication.accentureprep2app.repository

import com.example.simpletextcomposeapplication.accentureprep2app.data.remote.ArtItemResponse
import com.example.simpletextcomposeapplication.accentureprep2app.data.remote.ArtService2
import com.example.simpletextcomposeapplication.accentureprep2app.domain.ArtItem
import javax.inject.Inject

interface ArtRepository {
    suspend fun getArtList(): List<ArtItem>
}

class ArtRepositoryImpl @Inject constructor(
    private val service: ArtService2
) : ArtRepository {
    override suspend fun getArtList(): List<ArtItem> = service.getArtList().toDomain()
}

private fun List<ArtItemResponse>.toDomain(): List<ArtItem> = this.map { it.toDomain() }

private fun ArtItemResponse.toDomain(): ArtItem = ArtItem(
    this.id,
    this.title,
)
