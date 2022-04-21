package com.example.data.remote.api

import com.example.data.remote.model.CharacterResultModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {

    @GET("/api/character")
    suspend fun listCharacters(@Query("page") nextPageNumber: Int): CharacterResultModel
}
