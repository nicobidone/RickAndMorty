package com.example.domain.repository

import com.example.domain.entity.CharacterEntity

interface CharacterRepository {

    suspend fun getCharacters(): List<CharacterEntity>
    suspend fun getCharacter(id: Int): CharacterEntity
    suspend fun getInitCharacters(): List<CharacterEntity>
}
