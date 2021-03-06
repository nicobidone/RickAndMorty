package com.example.domain.usecase

import com.example.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    suspend fun getCharacters() = characterRepository.getCharacters()

    suspend fun getCharacter(id: Int) = characterRepository.getCharacter(id)

    suspend fun getInitCharacters() = characterRepository.getInitCharacters().ifEmpty { getCharacters() }
}
