package com.gprosper.stridetest.data.repo

import android.util.LruCache
import com.gprosper.stridetest.data.api.model.pokemon.PokemonDetail
import com.gprosper.stridetest.services.api.interfaces.PokemonService

/**
 * Created by gregprosper on 20,May,2021
 */
class PokemonRepository(private val pokemonService: PokemonService) {
    private val pokemonDetailCache: LruCache<String, PokemonDetail> by lazy {
        LruCache(100)
    }

    suspend fun getPokemon(limit: Int = 20, offset: Int = 0) = pokemonService.getPokemon(limit = limit, offset = offset)

    suspend fun getPokemonDetail(name: String) : PokemonDetail {
        return pokemonDetailCache.get(name) ?: pokemonService.getPokemonDetails(name)
    }
}