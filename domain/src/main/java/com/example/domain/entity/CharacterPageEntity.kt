package com.example.domain.entity

data class CharacterPageEntity(
    val data: List<CharacterEntity>,
    val currentPage: Int,
    val finalPage: Int
)
