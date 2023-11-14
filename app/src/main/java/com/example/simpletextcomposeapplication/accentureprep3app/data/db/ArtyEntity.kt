package com.example.simpletextcomposeapplication.accentureprep3app.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "arty"
)
class ArtyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String
)