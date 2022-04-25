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
                        Pair(
                            result.info?.next?.substringAfter(DELIMITER)?.toInt() ?: result.info?.pages?.toInt() ?: INVALID_PAGE,
                            result.info?.pages?.toInt() ?: INVALID_PAGE
                        )
                    )
                )
            }
        } catch (e: Exception) {
            ServiceResult.Error(e.toString())
        }
    }

    companion object {
        private const val INVALID_PAGE = 0
        private const val DELIMITER = "="
    }
}
