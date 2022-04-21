package com.example.data.local.entity

import androidx.room.Embedded
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
