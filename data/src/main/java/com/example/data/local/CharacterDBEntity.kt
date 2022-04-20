package com.example.data.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

data class CharacterData(
    @Embedded val user: CharacterDBEntity,

    @Relation(parentColumn = "id", entityColumn = "uid")
    val location: LocationDBEntity?,

    @Relation(parentColumn = "id", entityColumn = "uid")
    val origin: OriginDBEntity?,

    @Relation(parentColumn = "id", entityColumn = "uid")
    val episodes: EpisodeDBEntity?
)

@Entity
data class CharacterDBEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "species") val species: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "created") val created: String
)

@Entity
data class LocationDBEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "url") val url: String? = null,
    @ColumnInfo(name = "uid") val uid: Int?
)

@Entity
data class OriginDBEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "url") val url: String? = null,
    @ColumnInfo(name = "uid") val uid: Int?

)

@Entity
data class EpisodeDBEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int? = null,
    @ColumnInfo(name = "episode") val episode: String? = null,
    @ColumnInfo(name = "uid") val uid: Int?

)
