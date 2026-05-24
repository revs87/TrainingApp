package com.example.simpletextcomposeapplication.rxjava_exercises.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [RXUserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RXUserDatabase : RoomDatabase() {
    abstract fun userDao(): RXUserDao

    companion object {

        fun createDbForTesting(context: Context): RXUserDatabase {
            return Room.inMemoryDatabaseBuilder(
                context,
                RXUserDatabase::class.java
            ).build()
        }

    }
}