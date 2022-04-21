package com.example.domain.repository

import com.example.domain.entity.CharacterEntity
import com.example.domain.entity.CharacterPageEntity

interface CharacterRepository {

    suspend fun getCharacters(nextPage: Int): CharacterPageEntity
    suspend fun getCharacter(id: Int): CharacterEntity
}
