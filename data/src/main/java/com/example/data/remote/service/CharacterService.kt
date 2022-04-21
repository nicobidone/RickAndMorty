package com.example.data.remote.service

import com.example.data.remote.ServiceResult
import com.example.data.remote.api.CharacterApi
import com.example.data.remote.model.CharacterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class CharacterService @Inject constructor(private val retrofit: Retrofit) {

    suspend fun getCharacters(nextPageNumber: Int): ServiceResult<Pair<List<CharacterModel>, Pair<Int, Int>>> {
        val api = retrofit.create(CharacterApi::class.java)
        return try {
            withContext(Dispatchers.IO) {
                val result = api.listCharacters(nextPageNumber)
                ServiceResult.Success(
                    Pair(
                        result.results?.toList() ?: emptyList(),
                        Pair(result.info?.next?.substringAfter("=")?.toInt() ?: 0, result.info?.pages?.toInt() ?: 0)
                    )
                )
            }
        } catch (e: Exception) {
            ServiceResult.Error(e.toString())
        }
    }
}
