package com.example.simpletextcomposeapplication

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.Flow
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DatabaseTest {

    private lateinit var db: MyDatabase

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            context = InstrumentationRegistry.getInstrumentation().targetContext,
            klass = MyDatabase::class.java
        ).build()
    }
    @After
    fun closeDb() { db.close() }


    @Test
    fun testInsertAndRetrieve() {
        val user = UserEntity(1, "John Doe")
        db.myDao.insert(user)

        val retrievedUser = db.myDao.getUserById(1)

        assertEquals(retrievedUser, user)
    }
}


@Entity(
    tableName = "user"
)
private data class UserEntity(
    @PrimaryKey val id: Long,
    val name: String,
)


@Dao
private interface MyDao {
    @Query("SELECT * FROM user")
    fun getUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserById(id: Long): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity)
}


@Database(
    entities = [UserEntity::class],
    version = 1
)
private abstract class MyDatabase : RoomDatabase() {
    abstract val myDao: MyDao
}