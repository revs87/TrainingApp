package com.example.simpletextcomposeapplication.accentureprep2app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtDao2 {

    @Query("SELECT * FROM art")
    fun getAllArt(): Flow<List<ArtEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArt(art: ArtEntity)

}