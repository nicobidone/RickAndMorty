package com.example.data.repository

import com.example.data.local.CharacterDataBaseEntity
import com.example.data.remote.model.CharacterModel
import com.example.domain.entity.CharacterEntity

fun CharacterModel.mapToCharacterEntity() = CharacterEntity(
    id = this.id ?: 0,
    name = this.name ?: "",
    image = this.image ?: ""
)

fun CharacterModel.mapToCharacterDataBaseEntity() = CharacterDataBaseEntity(
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

fun CharacterDataBaseEntity.mapToCharacterEntity() = CharacterEntity(
    id = this.id ?: 0,
    name = this.name ?: "",
    image = this.image ?: ""
)
