package com.example.simpletextcomposeapplication.accentureprep3app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ArtyEntity::class],
    version = 1
)
abstract class ArtyDatabase : RoomDatabase() {
    abstract val dao: ArtyDao

    companion object {
        private const val TABLE_NAME = "arty_db"

        fun createDb(context: Context): ArtyDatabase = Room.databaseBuilder(
            context,
            ArtyDatabase::class.java,
            TABLE_NAME
        ).build()
    }
}