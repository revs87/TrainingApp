package com.example.simpletextcomposeapplication.meowfactsapp.data.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [MeowFact::class],
    version = 1
)
abstract class MeowFactsDatabase: RoomDatabase() {
    abstract val meowFactsDao: MeowFactsDao

    companion object {
        const val DATABASE_NAME = "meowFacts_db"
    }
}

fun createDb(context: Context): MeowFactsDatabase {
    return Room.databaseBuilder(
        context,
        MeowFactsDatabase::class.java,
        MeowFactsDatabase.DATABASE_NAME
    ).build()
}