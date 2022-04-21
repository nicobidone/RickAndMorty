package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.local.entity.CharacterDBEntity
import com.example.data.local.entity.CharacterData
import com.example.data.local.entity.EpisodeDBEntity
import com.example.data.local.entity.LocationDBEntity
import com.example.data.local.entity.OriginDBEntity

@Dao
interface CharacterDao {

    @Transaction
    @Query("SELECT * FROM CharacterDBEntity")
    fun getAll(): List<CharacterData>

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
}
