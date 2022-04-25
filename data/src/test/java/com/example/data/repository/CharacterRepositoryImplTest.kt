package com.example.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.data.local.dao.CharacterDao
import com.example.data.local.entity.CharacterDBEntity
import com.example.data.local.entity.EpisodeDBEntity
import com.example.data.local.entity.LocationDBEntity
import com.example.data.local.entity.OriginDBEntity
import com.example.data.local.preferences.LAST_PAGE
import com.example.data.local.preferences.NEXT_PAGE
import com.example.data.remote.ServiceResult
import com.example.data.remote.model.CharacterModel
import com.example.data.remote.service.CharacterService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CharacterRepositoryImplTest {

    private val validId = 1
    private val invalidId = 0

    private val characterService = mockk<CharacterService>()
    private val characterDao = mockk<CharacterDao>()
    private val dataStore = mockk<DataStore<Preferences>>()
    private val characterDBEntity = mockk<CharacterDBEntity>(relaxed = true)

    private val mockPref: Preferences = mockk()
    private val mockPages = mockk<Pair<Int, Int>>(relaxed = true)
    private val mockModel = mockk<CharacterModel>(relaxed = true)
    private val mockResponse = listOf(mockModel)

    private val repository = CharacterRepositoryImpl(characterService, characterDao, dataStore)

    @Test
    fun `get init characters success`() {
        runBlocking {
            coEvery { characterDao.getAll() } returns listOf(mockk(relaxed = true))

            val result = repository.getInitCharacters()

            assert(result.isNullOrEmpty().not())
        }
    }

    @Test
    fun `get init characters error`() {
        runBlocking {
            coEvery { characterDao.getAll() } returns emptyList()

            val result = repository.getInitCharacters()

            assert(result.isNullOrEmpty())
        }
    }

    @Test
    fun `get init character valid character`() {
        runBlocking {
            every { characterDBEntity.id } returns validId
            coEvery { characterDao.findById(validId.toString()) } returns characterDBEntity

            val result = repository.getCharacter(validId)

            assert(result.id != invalidId)
        }
    }

    @Test
    fun `get init character invalid character`() {
        runBlocking {
            every { characterDBEntity.id } returns invalidId
            coEvery { characterDao.findById(any()) } returns characterDBEntity

            val result = repository.getCharacter(validId)

            assert(result.id == invalidId)
        }
    }

    @Test
    fun `get characters first success`() {
        runBlocking {
            getCharacterMocks()

            every { mockPages.first } returns 1
            every { mockPages.second } returns 2

            val result = repository.getCharacters()

            coVerify { characterDao.getAll() }
            coVerify { characterService.getCharacters(any()) }
            assert(result.isNullOrEmpty().not())
        }
    }

    @Test
    fun `get characters first error`() {
        runBlocking {
            getCharacterMocks()

            coEvery { characterService.getCharacters(any()) } returns ServiceResult.Error("")
            coEvery { characterDao.getAll() } returns emptyList()

            val result = repository.getCharacters()

            coVerify(exactly = 0) { characterDao.insertAll(any<CharacterDBEntity>()) }
            coVerify { characterDao.getAll() }
            coVerify { characterService.getCharacters(any()) }
            assert(result.isNullOrEmpty())
        }
    }

    @Test
    fun `get characters fetch another page success`() {
        runBlocking {
            getCharacterMocks()

            every { mockPages.first } returns 1
            every { mockPages.second } returns 2

            repository.getCharacters()

            every { mockPages.first } returns 2
            every { mockPages.second } returns 2

            val result = repository.getCharacters()

            coVerify(exactly = 2) { characterDao.getAll() }
            coVerify(exactly = 2) { characterService.getCharacters(any()) }
            assert(result.isNullOrEmpty().not())
        }
    }

    private fun getCharacterMocks() {
        // mockPreferences
        coEvery { dataStore.data } returns flow { emit(mockPref) }
        coEvery { dataStore.updateData(any()) } returns mockk()
        coEvery { mockPref[NEXT_PAGE] } returns 0
        coEvery { mockPref[LAST_PAGE] } returns 0
        //  mockService
        every { mockModel.episode } returns arrayListOf("")
        coEvery { characterService.getCharacters(any()) } returns ServiceResult.Success(Pair(mockResponse, mockPages))
        // mockDao
        coEvery { characterDao.insertAll(any<CharacterDBEntity>()) } returns Unit
        coEvery { characterDao.insertAll(any<LocationDBEntity>()) } returns Unit
        coEvery { characterDao.insertAll(any<OriginDBEntity>()) } returns Unit
        coEvery { characterDao.insertAll(any<EpisodeDBEntity>()) } returns Unit
        coEvery { characterDao.getAll() } returns listOf(mockk(relaxed = true))
    }
}
