package com.example.rickandmorty.feature.character

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.entity.CharacterPageEntity
import com.example.domain.usecase.CharacterUseCase
import com.example.rickandmorty.BaseViewModelTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainViewModelTest : BaseViewModelTest() {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val characterUseCase = mockk<CharacterUseCase>()
    private val viewModel = MainViewModel(characterUseCase)

    @Test
    fun `get data with success and return list`() {
        runBlocking {
            coEvery { characterUseCase.getCharacters(any()) } returns CharacterPageEntity(listOf(mockk()), 1, 2)
            viewModel.getCharacters()
            assert(viewModel.charactersLiveData.value.isNullOrEmpty().not())
        }
    }

    @Test
    fun `get another data with success and return list`() {
        runBlocking {
            coEvery { characterUseCase.getCharacters(any()) } returns CharacterPageEntity(listOf(mockk()), 1, 2)
            viewModel.getCharacters()
            viewModel.getCharacters()
            assert(viewModel.charactersLiveData.value.isNullOrEmpty().not())
        }
    }

    @Test
    fun `get all possible data with success and return list`() {
        runBlocking {
            coEvery { characterUseCase.getCharacters(any()) } returns CharacterPageEntity(listOf(mockk()), 1, 2)
            viewModel.getCharacters()
            coEvery { characterUseCase.getCharacters(any()) } returns CharacterPageEntity(listOf(mockk()), 2, 2)
            viewModel.getCharacters()
            viewModel.getCharacters()
            assert(viewModel.charactersLiveData.value.isNullOrEmpty().not())
        }
    }

    @Test
    fun `get no data on failed retrive list`() {
        runBlocking {
            coEvery { characterUseCase.getCharacters(any()) } returns CharacterPageEntity(emptyList(), 0, 0)
            viewModel.getCharacters()
            assert(viewModel.charactersLiveData.value.isNullOrEmpty())
        }
    }
}
