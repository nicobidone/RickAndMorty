package com.example.domain.entity

data class CharacterEntity(
    val id: Int,
    val name: String,
    val image: String,
    var species: String,
    var type: String,
    var gender: String,
    var url: String,
    var created: String
)
