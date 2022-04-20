package com.example.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterModel(
    @SerialName("id") var id: Int? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("status") var status: String? = null,
    @SerialName("species") var species: String? = null,
    @SerialName("type") var type: String? = null,
    @SerialName("gender") var gender: String? = null,
    @SerialName("origin") var origin: OriginModel? = OriginModel(),
    @SerialName("location") var location: LocationModel? = LocationModel(),
    @SerialName("image") var image: String? = null,
    @SerialName("episode") var episode: ArrayList<String> = arrayListOf(),
    @SerialName("url") var url: String? = null,
    @SerialName("created") var created: String? = null
)
