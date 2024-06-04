package com.example.simpletextcomposeapplication.itunestop100.domain.repository

import com.example.simpletextcomposeapplication.itunestop100.domain.model.ITunesAlbum
import kotlinx.coroutines.flow.Flow

interface ITunesAlbumsRepository {
    fun getTop100Albums(): Flow<List<ITunesAlbum>>
    fun refresh()
}