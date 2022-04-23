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
    val pageLiveData by lazy { MutableLiveData<Int>() }

    private var lastPage = 2

    fun setInitPage(page: Int) {
        pageLiveData.value = page
        lastPage = page + 1
        getInitCharacters()
    }

    private fun getInitCharacters() {
        viewModelScope.launch {
            with(useCase.getInitCharacters()) {
                charactersLiveData.value = this
                if (this.isEmpty()) getCharacters()
            }
        }
    }

    fun getCharacters() {
        viewModelScope.launch {
            pageLiveData.value?.let { page ->
                with(useCase.getCharacters(page)) {
                    if (page <= lastPage) charactersLiveData.value = this.data
                    pageLiveData.value = page + 1
                    if (this.finalPage > 0) lastPage = this.finalPage
                }
            }
        }
    }
}
