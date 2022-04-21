package com.example.data.repository

import com.example.data.local.CharacterDao
import com.example.data.mapToCharacterDataBaseEntity
import com.example.data.mapToCharacterEntity
import com.example.data.mapToEpisodeDBEntity
import com.example.data.mapToLocationDBEntity
import com.example.data.mapToOriginDBEntity
import com.example.data.remote.ServiceResult
import com.example.data.remote.service.CharacterService
import com.example.domain.entity.CharacterEntity
import com.example.domain.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterService: CharacterService,
    private val characterDao: CharacterDao
) : CharacterRepository {

    override suspend fun getCharacters(): List<CharacterEntity> = withContext(Dispatchers.IO) {
        with(characterService.getCharacters()) {
            when (this) {
                is ServiceResult.Success -> {
                    characterDao.insertAll(*this.data.map { it.mapToCharacterDataBaseEntity() }.toTypedArray())
                    characterDao.insertAll(*this.data.map { it.mapToLocationDBEntity() }.toTypedArray())
                    characterDao.insertAll(*this.data.map { it.mapToOriginDBEntity() }.toTypedArray())
                    this.data.map { episodeList ->
                        characterDao.insertAll(*episodeList.episode.map { it.mapToEpisodeDBEntity(episodeList.id ?: 0) }.toTypedArray())
                    }
                    characterDao.getAll().map { it.mapToCharacterEntity() }
                }
                else -> emptyList()
            }
        }
    }

    override suspend fun getCharacter(id: Int): CharacterEntity = withContext(Dispatchers.IO) {
        characterDao.findById(id.toString()).mapToCharacterEntity()
    }
}
