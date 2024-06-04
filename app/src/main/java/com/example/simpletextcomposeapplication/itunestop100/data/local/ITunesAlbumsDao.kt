package com.example.simpletextcomposeapplication.itunestop100.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ITunesAlbumsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(albums: List<ITunesAlbumsEntity>)

    @Query("SELECT * FROM albums")
    fun getAll(): Flow<List<ITunesAlbumsEntity>>

}