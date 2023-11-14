package com.example.simpletextcomposeapplication.accentureprep3app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtyDao {

    @Query("SELECT * FROM arty")
    fun getArtyList(): Flow<List<ArtyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArty(arty: ArtyEntity)

}