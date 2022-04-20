package com.example.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OriginModel(
    @SerialName("name") var name: String? = null,
    @SerialName("url") var url: String? = null

)
