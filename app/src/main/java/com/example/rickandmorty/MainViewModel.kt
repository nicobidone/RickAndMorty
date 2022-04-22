package com.example.rickandmorty

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.CharacterEntity
import com.example.domain.usecase.CharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: CharacterUseCase) : ViewModel() {

    val charactersLiveData by lazy { MutableLiveData<List<CharacterEntity>>() }

    private var page = 1
    private var lastPage = 2

    fun getInitCharacters() {
        viewModelScope.launch {
            with(useCase.getInitCharacters()) {
                charactersLiveData.value = this
                if (this.isEmpty()) getCharacters()
            }
        }
    }

    fun getCharacters() {
        viewModelScope.launch {
            with(useCase.getCharacters(page)) {
                if (page <= lastPage) charactersLiveData.value = this.data
                page++
                if (this.finalPage > 0) lastPage = this.finalPage
            }
        }
    }
}
