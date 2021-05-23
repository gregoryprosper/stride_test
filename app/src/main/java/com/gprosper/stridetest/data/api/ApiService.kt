package com.gprosper.stridetest.services.api

import com.gprosper.stridetest.data.api.adapters.PropertyNameAdapter
import com.gprosper.stridetest.services.api.interfaces.PokemonService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by gregprosper on 20,May,2021
 */
object ApiService {
    private const val POKEMON_BASE_URL = "https://pokeapi.co/api/v2/"

    val moshi = Moshi.Builder()
        .add(PropertyNameAdapter())
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(POKEMON_BASE_URL)
            .build()
    }

    val pokemonService: PokemonService by lazy {
        retrofit().create(PokemonService::class.java)
    }
}