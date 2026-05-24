package com.example.simpletextcomposeapplication.rxjava_exercises.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RXUserDao {
    @Query("SELECT * FROM rxusers")
    fun getUsers(): List<RXUserEntity>

    @Query("SELECT * FROM rxusers WHERE username = :username")
    fun getUser(username: String): RXUserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<RXUserEntity>)

    @Query("SELECT EXISTS (SELECT 1 FROM rxusers WHERE username = :username)")
    fun userExists(username: String): Boolean
}
