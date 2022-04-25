package com.example.rickandmorty.feature.character

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
    fun `get characters success`() {
        runBlocking {
            coEvery { characterUseCase.getCharacters() } returns listOf(mockk())
            viewModel.getCharacters()
            assert(viewModel.charactersLiveData.value.isNullOrEmpty().not())
        }
    }

    @Test
    fun `get characters error`() {
        runBlocking {
            coEvery { characterUseCase.getCharacters() } returns emptyList()
            viewModel.getCharacters()
            assert(viewModel.charactersLiveData.value.isNullOrEmpty())
        }
    }

    @Test
    fun `get init characters first time success`() {
        runBlocking {
            coEvery { characterUseCase.getInitCharacters() } returns listOf(mockk())

            viewModel.getInitCharacters()

            assert(viewModel.charactersLiveData.value.isNullOrEmpty().not())
        }
    }

    @Test
    fun `get init characters first time failed`() {
        runBlocking {
            coEvery { characterUseCase.getInitCharacters() } returns emptyList()

            viewModel.getInitCharacters()

            assert(viewModel.charactersLiveData.value.isNullOrEmpty())
        }
    }
}
