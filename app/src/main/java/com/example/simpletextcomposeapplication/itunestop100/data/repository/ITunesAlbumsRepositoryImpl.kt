package com.example.simpletextcomposeapplication.itunestop100.data.repository

import com.example.simpletextcomposeapplication.itunestop100.data.local.ITunesAlbumsDatabase
import com.example.simpletextcomposeapplication.itunestop100.data.local.ITunesAlbumsEntity
import com.example.simpletextcomposeapplication.itunestop100.data.remote.Entry
import com.example.simpletextcomposeapplication.itunestop100.data.remote.ITunesAlbumsResponse
import com.example.simpletextcomposeapplication.itunestop100.data.remote.ITunesAlbumsService
import com.example.simpletextcomposeapplication.itunestop100.domain.model.ITunesAlbum
import com.example.simpletextcomposeapplication.itunestop100.domain.repository.ITunesAlbumsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class ITunesAlbumsRepositoryImpl(
    private val service: ITunesAlbumsService,
    private val db: ITunesAlbumsDatabase
) : ITunesAlbumsRepository {

    override fun getTop100Albums(): Flow<List<ITunesAlbum>> =
        db.dao.getAll()
            .map { it.toDomain() }
            .flowOn(Dispatchers.IO)

    override fun refresh() {
        CoroutineScope(Dispatchers.IO).launch {
            val albums = service.getAlbums()
            db.dao.insert(albums = albums.feed.entry.toEntity())
        }
    }
}


private fun List<ITunesAlbumsEntity>.toDomain(): List<ITunesAlbum> =
    this.map { ITunesAlbum(it.id, it.name) }

private fun ITunesAlbumsResponse.toDomain(): List<ITunesAlbum> =
    this.feed.entry.map { ITunesAlbum(it.id.label, it.title.label) }

private fun List<Entry>.toEntity(): List<ITunesAlbumsEntity> =
    this.map { ITunesAlbumsEntity(it.id.label, it.title.label) }
