package com.example.data

import com.example.data.local.entity.CharacterDBEntity
import com.example.data.local.entity.CharacterData
import com.example.data.local.entity.EpisodeDBEntity
import com.example.data.local.entity.LocationDBEntity
import com.example.data.local.entity.OriginDBEntity
import com.example.data.remote.model.CharacterModel
import com.example.domain.entity.CharacterEntity

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
    image = this.image ?: "",
    species = species ?: "",
    type = type ?: "",
    gender = gender ?: "",
    url = url ?: "",
    created = created ?: ""
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
    id = this.user.id ?: 0,
    name = this.user.name ?: "",
    image = this.user.image ?: "",
    species = this.user.species ?: "",
    type = this.user.type ?: "",
    gender = this.user.gender ?: "",
    url = this.user.url ?: "",
    created = this.user.created ?: ""
)

fun String.mapToEpisodeDBEntity(id: Int) = EpisodeDBEntity(
    episode = this,
    uid = id
)
