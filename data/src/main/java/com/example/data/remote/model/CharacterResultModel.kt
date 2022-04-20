package com.example.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterResultModel(
    val info: InfoModel? = null,
    val results: List<CharacterModel>? = emptyList()
)
