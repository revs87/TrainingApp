package com.example.simpletextcomposeapplication.accentureprep2app.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "art"
)
data class ArtEntity(
    @PrimaryKey val id: Long,
    val title: String,
)