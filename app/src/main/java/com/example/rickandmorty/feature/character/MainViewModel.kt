package com.example.rickandmorty.feature.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.CharacterEntity
import com.example.domain.usecase.CharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: CharacterUseCase) : ViewModel() {

    val charactersLiveData by lazy { MutableLiveData<List<CharacterEntity>>() }

    fun getInitCharacters() {
        viewModelScope.launch {
            with(useCase.getInitCharacters()) {
                charactersLiveData.value = this
            }
        }
    }

    fun getCharacters() {
        viewModelScope.launch {
            with(useCase.getCharacters()) {
                charactersLiveData.value = this
            }
        }
    }
}
