package com.example.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class InfoModel(
    val count: Long? = null,
    val pages: Long? = null,
    val next: String? = null,
    val prev: String? = null
)
