package com.example.data.repository

import com.example.data.local.dao.CharacterDao
import com.example.data.local.entity.CharacterDBEntity
import com.example.data.local.entity.CharacterData
import com.example.data.local.entity.EpisodeDBEntity
import com.example.data.local.entity.LocationDBEntity
import com.example.data.local.entity.OriginDBEntity
import com.example.data.remote.ServiceResult
import com.example.data.remote.model.CharacterModel
import com.example.data.remote.model.LocationModel
import com.example.data.remote.model.OriginModel
import com.example.data.remote.service.CharacterService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CharacterRepositoryImplTest {

    private val characterService = mockk<CharacterService>()
    private val characterDao = mockk<CharacterDao>()
    private val repository = CharacterRepositoryImpl(characterService, characterDao)
    private val id = 1
    private val page = 1

    @Test
    fun `get data with success and return list`() {
        runBlocking {
            val characterList = listOf(CharacterModel(id, "", "", "", "", "", OriginModel(), LocationModel(), "", arrayListOf(""), "", ""))
            val character = CharacterDBEntity(id, "", "", "", "", "", "", "", "")
            val currentPage = 1
            val lastPage = 2

            coEvery { characterService.getCharacters(page) } returns ServiceResult.Success(Pair(characterList, Pair(currentPage, lastPage)))
            coEvery { characterDao.insertAll(* listOf(any<CharacterDBEntity>()).toTypedArray()) } returns Unit
            coEvery { characterDao.insertAll(* listOf(any<LocationDBEntity>()).toTypedArray()) } returns Unit
            coEvery { characterDao.insertAll(* listOf(any<OriginDBEntity>()).toTypedArray()) } returns Unit
            coEvery { characterDao.insertAll(* listOf(any<EpisodeDBEntity>()).toTypedArray()) } returns Unit
            coEvery { characterDao.getAll() } returns listOf(CharacterData(character, mockk(), mockk(), mockk()))

            val result = repository.getCharacters(page)

            assert(result.data.first().id == id)
            assert(result.currentPage == currentPage)
            assert(result.finalPage == lastPage)
        }
    }

    @Test
    fun `get data with failure and return list`() {
        runBlocking {
            val character = CharacterDBEntity(id, "", "", "", "", "", "", "", "")
            val pageError = 0

            coEvery { characterService.getCharacters(page) } returns ServiceResult.Error("")
            coEvery { characterDao.getAll() } returns listOf(CharacterData(character, mockk(), mockk(), mockk()))

            val result = repository.getCharacters(page)

            assert(result.data.first().id == id)
            assert(result.currentPage == pageError)
            assert(result.finalPage == pageError)
        }
    }
}
