package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {

    @Query("SELECT * FROM CharacterDataBaseEntity")
    fun getAll(): List<CharacterDataBaseEntity>

    @Query("SELECT * FROM CharacterDataBaseEntity WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<CharacterDataBaseEntity>

    @Query("SELECT * FROM CharacterDataBaseEntity WHERE id == :charId")
    fun findById(charId: String): CharacterDataBaseEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: CharacterDataBaseEntity)

    @Delete
    fun delete(user: CharacterDataBaseEntity)
}
