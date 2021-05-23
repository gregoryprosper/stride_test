package com.gprosper.stridetest.data.api.model.pokemon

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by gregprosper on 20,May,2021
 */
data class Pokemon(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)