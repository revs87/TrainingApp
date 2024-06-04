package com.example.simpletextcomposeapplication.itunestop100.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ITunesAlbumsEntity::class],
    version = 1
)
abstract class ITunesAlbumsDatabase : RoomDatabase() {
    abstract val dao: ITunesAlbumsDao

    companion object {
        private const val DB_NAME = "iTunesAlbums.db"

        fun createDb(context: Context): ITunesAlbumsDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = ITunesAlbumsDatabase::class.java,
                name = DB_NAME
            ).build()
        }

        fun createDbForTesting(context: Context): ITunesAlbumsDatabase {
            return Room.inMemoryDatabaseBuilder(
                context = context,
                klass = ITunesAlbumsDatabase::class.java,
            ).build()
        }
    }
}