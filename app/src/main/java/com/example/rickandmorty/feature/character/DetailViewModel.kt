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
class DetailViewModel @Inject constructor(private val useCase: CharacterUseCase) : ViewModel() {

    val characterLiveData by lazy { MutableLiveData<CharacterEntity>() }

    fun getCharacter(id: Int) {
        viewModelScope.launch {
            characterLiveData.value = useCase.getCharacter(id)
        }
    }
}
