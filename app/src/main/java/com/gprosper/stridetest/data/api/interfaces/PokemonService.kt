package com.gprosper.stridetest.services.api.interfaces

import com.gprosper.stridetest.data.PaginationResult
import com.gprosper.stridetest.data.api.model.pokemon.Pokemon
import com.gprosper.stridetest.data.api.model.pokemon.PokemonDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by gregprosper on 20,May,2021
 */
interface PokemonService {
    @GET("pokemon")
    suspend fun getPokemon(@Query("limit") limit: Int, @Query("offset") offset: Int): PaginationResult<Pokemon>

    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(@Path("name") id: String): PokemonDetail
}