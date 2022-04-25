package com.example.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.data.local.dao.CharacterDao
import com.example.data.local.preferences.DEFAULT_PAGE_VALUE
import com.example.data.local.preferences.LAST_PAGE
import com.example.data.local.preferences.NEXT_PAGE
import com.example.data.mapToCharacterDataBaseEntity
import com.example.data.mapToCharacterEntity
import com.example.data.mapToEpisodeDBEntity
import com.example.data.mapToLocationDBEntity
import com.example.data.mapToOriginDBEntity
import com.example.data.remote.ServiceResult
import com.example.data.remote.model.CharacterModel
import com.example.data.remote.service.CharacterService
import com.example.domain.entity.CharacterEntity
import com.example.domain.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterService: CharacterService,
    private val characterDao: CharacterDao,
    private val dataStore: DataStore<Preferences>
) : CharacterRepository {

    override suspend fun getCharacters(): List<CharacterEntity> = withContext(Dispatchers.IO) {
        var nextPage: Int
        var lastPage: Int

        with(dataStore.data.first()) {
            nextPage = this[NEXT_PAGE] ?: DEFAULT_PAGE_VALUE
            lastPage = this[LAST_PAGE] ?: DEFAULT_PAGE_VALUE
        }

        if (nextPage <= lastPage) {
            with(characterService.getCharacters(nextPage)) {
                if (this is ServiceResult.Success) {
                    updateDataBase(this.data.first)
                    updatePages(this.data.second.first,this.data.second.second)
                }
            }
        }
        characterDao.getAll().map { it.mapToCharacterEntity() }
    }

    private fun updateDataBase(data: List<CharacterModel>) {
        characterDao.insertAll(*data.map { it.mapToCharacterDataBaseEntity() }.toTypedArray())
        characterDao.insertAll(*data.map { it.mapToLocationDBEntity() }.toTypedArray())
        characterDao.insertAll(*data.map { it.mapToOriginDBEntity() }.toTypedArray())
        data.map { episodeList ->
            characterDao.insertAll(*episodeList.episode.map { it.mapToEpisodeDBEntity(episodeList.id ?: INVALID_ID) }.toTypedArray())
        }
    }

    private suspend fun updatePages(first : Int, second : Int) {
        dataStore.edit { settings -> settings[NEXT_PAGE] = first }
        dataStore.edit { settings -> settings[LAST_PAGE] = second }
    }

    override suspend fun getCharacter(id: Int) = withContext(Dispatchers.IO) {
        characterDao.findById(id.toString()).mapToCharacterEntity()
    }

    override suspend fun getInitCharacters() = withContext(Dispatchers.IO) {
        characterDao.getAll().map { it.mapToCharacterEntity() }
    }

    companion object {
        private const val INVALID_ID = 0
    }
}
