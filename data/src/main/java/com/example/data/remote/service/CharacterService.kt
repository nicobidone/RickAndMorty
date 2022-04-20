package com.example.data.remote.service

import com.example.data.remote.model.CharacterModel
import com.example.data.remote.ServiceResult
import com.example.data.remote.api.CharacterApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class CharacterService @Inject constructor(private val retrofit: Retrofit) {

    suspend fun getCharacters(): ServiceResult<List<CharacterModel>> {
        val api = retrofit.create(CharacterApi::class.java)
        return try {
            withContext(Dispatchers.IO) {
                ServiceResult.Success(api.listCharacters().results?.toList() ?: emptyList())
            }
        } catch (e: Exception) {
            ServiceResult.Error(e.toString())
        }
    }
}
