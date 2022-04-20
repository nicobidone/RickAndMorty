package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CharacterDao {

    @Transaction
    @Query("SELECT * FROM CharacterDBEntity")
    fun getAll(): List<CharacterData>

    @Query("SELECT * FROM CharacterDBEntity WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<CharacterDBEntity>

    @Query("SELECT * FROM CharacterDBEntity WHERE id == :charId")
    fun findById(charId: String): CharacterDBEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg users: CharacterDBEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg users: LocationDBEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg users: OriginDBEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg users: EpisodeDBEntity)

    @Delete
    fun delete(user: CharacterDBEntity)
}
