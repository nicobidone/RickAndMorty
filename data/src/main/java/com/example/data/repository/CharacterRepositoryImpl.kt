package com.example.data.repository

import com.example.data.remote.ServiceResult
import com.example.data.remote.service.CharacterService
import com.example.domain.entity.CharacterEntity
import com.example.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val characterService: CharacterService) : CharacterRepository {

    override suspend fun getCharacters(): List<CharacterEntity> =
        with(characterService.getCharacters()) {
            when (this) {
                is ServiceResult.Success -> this.data.map { it.mapToCharacterEntity() }
                else -> emptyList()
            }
        }
}
