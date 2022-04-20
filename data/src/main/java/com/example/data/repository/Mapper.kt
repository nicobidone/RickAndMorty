package com.example.data.repository

import com.example.data.remote.model.CharacterModel
import com.example.domain.entity.CharacterEntity

fun CharacterModel.mapToCharacterEntity() = CharacterEntity(
    id = this.id ?: 0,
    name = this.name ?: "",
    image = this.image ?: ""
)
