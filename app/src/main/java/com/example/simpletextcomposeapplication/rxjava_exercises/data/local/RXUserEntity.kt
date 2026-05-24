package com.example.simpletextcomposeapplication.rxjava_exercises.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "rxusers",
    indices = [Index(value = ["username"], unique = true)]
)
data class RXUserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val username: String,
    val lastLogin: Long
)
