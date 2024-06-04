package com.example.simpletextcomposeapplication.itunestop100.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "albums"
)
class ITunesAlbumsEntity(
    @PrimaryKey val id: String,
    val name: String
)