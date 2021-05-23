package com.gprosper.stridetest.data.api.model.pokemon

import com.gprosper.stridetest.data.api.adapters.PropertyName
import com.squareup.moshi.Json

/**
 * Created by gregprosper on 20,May,2021
 */

data class PokemonDetail(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "order")
    val order: Int,
    @Json(name = "sprites")
    val sprites: Sprites,
    @Json(name = "weight")
    val weight: Int,
    @Json(name = "base_experience")
    val exp: Int,
    @Json(name = "moves")
    val moves: List<Move>
) {
    val nameCap: String
        get() = name.replaceFirstChar { it.uppercase() }

    val levelUpMoves: List<Move>
        get() = moves
            .filter { it.versionGroupDetails.any { it.versionGroup == "ultra-sun-ultra-moon" } }
            .sortedWith(compareBy({it.versionGroupDetails.last().moveLearnMethod}, {it.versionGroupDetails.last().levelLearnedAt}))
}

data class Sprites(
    @Json(name = "front_default")
    val frontDefault: String?
)

data class Move(
    @PropertyName
    @Json(name = "move")
    val name: String,
    @Json(name = "version_group_details")
    val versionGroupDetails: List<VersionGroupDetails>
)

data class VersionGroupDetails(
    @Json(name = "level_learned_at")
    val levelLearnedAt: Int,
    @Json(name = "move_learn_method")
    @PropertyName
    val moveLearnMethod: String,
    @Json(name = "version_group")
    @PropertyName
    val versionGroup: String
)