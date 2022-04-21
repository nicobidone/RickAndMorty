package com.example.rickandmorty

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.entity.CharacterEntity
import com.example.domain.usecase.CharacterUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest : BaseViewModelTest() {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val characterUseCase = mockk<CharacterUseCase>()
    private val viewModel = DetailViewModel(characterUseCase)
    private val characterEntity = mockk<CharacterEntity>()

    @Test
    fun `get data with success and return list`() {
        runBlocking {
            val charId = 1
            every { characterEntity.id } returns 1
            coEvery { characterUseCase.getCharacter(charId) } returns characterEntity
            viewModel.getCharacter(charId)
            assert(viewModel.characterLiveData.value?.id != 0)
        }
    }

    @Test
    fun `get data with no success and return list`() {
        runBlocking {
            val charId = 1
            every { characterEntity.id } returns 0
            coEvery { characterUseCase.getCharacter(charId) } returns characterEntity
            viewModel.getCharacter(charId)
            assert(viewModel.characterLiveData.value?.id == 0)
        }
    }
}
