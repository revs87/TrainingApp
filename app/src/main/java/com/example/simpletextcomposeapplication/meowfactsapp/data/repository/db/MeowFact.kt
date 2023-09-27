package com.example.simpletextcomposeapplication.meowfactsapp.data.repository.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "meowFact"
)
class MeowFact(
    val message: String,
    @PrimaryKey(autoGenerate = true) val id: Long? = null
)