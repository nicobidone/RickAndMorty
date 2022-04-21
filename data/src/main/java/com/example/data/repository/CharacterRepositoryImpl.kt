package com.example.data.repository

import com.example.data.local.dao.CharacterDao
import com.example.data.mapToCharacterDataBaseEntity
import com.example.data.mapToCharacterEntity
import com.example.data.mapToEpisodeDBEntity
import com.example.data.mapToLocationDBEntity
import com.example.data.mapToOriginDBEntity
import com.example.data.remote.ServiceResult
import com.example.data.remote.service.CharacterService
import com.example.domain.entity.CharacterEntity
import com.example.domain.entity.CharacterPageEntity
import com.example.domain.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterService: CharacterService,
    private val characterDao: CharacterDao
) : CharacterRepository {

    override suspend fun getCharacters(nextPage: Int): CharacterPageEntity = withContext(Dispatchers.IO) {
        with(characterService.getCharacters(nextPage)) {
            when (this) {
                is ServiceResult.Success -> {
                    characterDao.insertAll(*this.data.first.map { it.mapToCharacterDataBaseEntity() }.toTypedArray())
                    characterDao.insertAll(*this.data.first.map { it.mapToLocationDBEntity() }.toTypedArray())
                    characterDao.insertAll(*this.data.first.map { it.mapToOriginDBEntity() }.toTypedArray())
                    this.data.first.map { episodeList ->
                        characterDao.insertAll(*episodeList.episode.map { it.mapToEpisodeDBEntity(episodeList.id ?: 0) }.toTypedArray())
                    }
                    CharacterPageEntity(
                        characterDao.getAll().map { it.mapToCharacterEntity() },
                        this.data.second.first,
                        this.data.second.second
                    )
                }
                else -> CharacterPageEntity(
                    characterDao.getAll().map { it.mapToCharacterEntity() },
                    0,
                    0
                )
            }
        }
    }

    override suspend fun getCharacter(id: Int): CharacterEntity = withContext(Dispatchers.IO) {
        characterDao.findById(id.toString()).mapToCharacterEntity()
    }
}
