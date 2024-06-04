package com.example.simpletextcomposeapplication.accentureprep2app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [ArtEntity::class],
    version = 1
)
abstract class ArtDatabase : RoomDatabase() {
    abstract val artDao: ArtDao2

    companion object {
        private const val DB_NAME = "art_db"

        fun createDb(context: Context): ArtDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = ArtDatabase::class.java,
                name = ArtDatabase.DB_NAME
            )
                .build()
        }

        fun createDbForTesting(context: Context): ArtDatabase {
            return Room.inMemoryDatabaseBuilder(
                context = context,
                klass = ArtDatabase::class.java
            ).build()
        }
    }
}
