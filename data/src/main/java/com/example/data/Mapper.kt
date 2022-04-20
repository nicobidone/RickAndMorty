package com.example.data.repository

import com.example.data.local.CharacterData
import com.example.data.local.CharacterDBEntity
import com.example.data.local.EpisodeDBEntity
import com.example.data.local.LocationDBEntity
import com.example.data.local.OriginDBEntity
import com.example.data.remote.model.CharacterModel
import com.example.domain.entity.CharacterEntity

fun CharacterModel.mapToCharacterEntity() = CharacterEntity(
    id = this.id ?: 0,
    name = this.name ?: "",
    image = this.image ?: ""
)

fun CharacterModel.mapToCharacterDataBaseEntity() = CharacterDBEntity(
    id = this.id ?: 0,
    name = this.name ?: "",
    status = this.status ?: "",
    species = this.species ?: "",
    type = this.type ?: "",
    gender = this.gender ?: "",
    image = this.image ?: "",
    url = this.url ?: "",
    created = this.created ?: ""
)

fun CharacterDBEntity.mapToCharacterEntity() = CharacterEntity(
    id = this.id ?: 0,
    name = this.name ?: "",
    image = this.image ?: ""
)

fun CharacterModel.mapToOriginDBEntity() = OriginDBEntity(
    name = this.origin?.name,
    url = this.origin?.url,
    uid = this.id
)

fun CharacterModel.mapToLocationDBEntity() = LocationDBEntity(
    name = this.location?.name,
    url = this.location?.url,
    uid = this.id
)

fun CharacterData.mapToCharacterEntity() = CharacterEntity(
    id = this.user.id,
    name = this.user.name,
    image = this.user.image
)

fun String.mapToEpisodeDBEntity(id: Int) = EpisodeDBEntity(
    episode = this,
    uid = id
)
