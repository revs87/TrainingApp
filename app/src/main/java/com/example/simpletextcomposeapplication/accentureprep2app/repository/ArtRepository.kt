package com.example.simpletextcomposeapplication.accentureprep2app.repository

import com.example.simpletextcomposeapplication.accentureprep2app.data.db.ArtDao2
import com.example.simpletextcomposeapplication.accentureprep2app.data.db.ArtEntity
import com.example.simpletextcomposeapplication.accentureprep2app.data.remote.ArtItemDetailsResponse
import com.example.simpletextcomposeapplication.accentureprep2app.data.remote.ArtItemResponse
import com.example.simpletextcomposeapplication.accentureprep2app.data.remote.ArtService2
import com.example.simpletextcomposeapplication.accentureprep2app.domain.ArtDetails
import com.example.simpletextcomposeapplication.accentureprep2app.domain.ArtItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ArtRepository {
    suspend fun getArtList(): List<ArtItem>

    fun getSavedArtItems(): Flow<List<ArtItem>>

    suspend fun saveArtItems(list: List<ArtItem>)

    suspend fun getArtDetails(id: Long): ArtDetails
}

class ArtRepositoryImpl @Inject constructor(
    private val service: ArtService2,
    private val dao: ArtDao2,
) : ArtRepository {
    override suspend fun getArtList(): List<ArtItem> = service.getArtList().toDomain()

    override fun getSavedArtItems(): Flow<List<ArtItem>> = dao.getAllArt().map { artEntities -> artEntities.toDomain() }

    override suspend fun saveArtItems(list: List<ArtItem>) = list.forEach { dao.insertArt(it.toEntity()) }

    private fun List<ArtItemResponse>.toDomain(): List<ArtItem> = this.map { it.toDomain() }

    private fun ArtItemResponse.toDomain(): ArtItem = ArtItem(
        this.id,
        this.title,
    )

    override suspend fun getArtDetails(id: Long): ArtDetails = service.getArtDetails(id).toDomain()

    private fun ArtItemDetailsResponse.toDomain(): ArtDetails = ArtDetails(
        this.id,
        this.title,
        this.description,
        this.imageId,
    )
}

private fun List<ArtEntity>.toDomain(): List<ArtItem> = this.map { ArtItem(it.id, it.title) }

private fun ArtItem.toEntity(): ArtEntity = ArtEntity(this.id, this.title)