package com.example.rickandmorty

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

    fun getCharacters() {
        viewModelScope.launch {
            charactersLiveData.value = useCase.getCharacters()
        }
    }
}
