package com.example.data.remote.api

import com.example.data.remote.model.CharacterResultModel
import retrofit2.http.GET

interface CharacterApi {

    @GET("/api/character")
    suspend fun listCharacters(): CharacterResultModel
}
