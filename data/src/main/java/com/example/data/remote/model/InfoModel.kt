package com.example.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class InfoModel(
    val count: Long,
    val pages: Long,
    val next: String,
    val prev: CharacterModel? = null
)
