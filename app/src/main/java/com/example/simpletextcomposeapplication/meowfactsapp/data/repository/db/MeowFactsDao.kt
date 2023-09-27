package com.example.simpletextcomposeapplication.meowfactsapp.data.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MeowFactsDao {

    @Query("SELECT * FROM meowFact")
    fun getMeowFacts(): List<MeowFact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeowFact(meowFact: MeowFact)
}