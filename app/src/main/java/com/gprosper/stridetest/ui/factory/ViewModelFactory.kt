package com.gprosper.stridetest.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gprosper.stridetest.data.repo.PokemonRepository
import com.gprosper.stridetest.services.api.ApiService
import com.gprosper.stridetest.ui.pokemon.PokemonDetailViewModel
import com.gprosper.stridetest.ui.pokemon.PokemonViewModel

/**
 * Created by gregprosper on 20,May,2021
 */
class ViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonViewModel::class.java)) {
            return PokemonViewModel(PokemonRepository(ApiService.pokemonService)) as T
        }
        if (modelClass.isAssignableFrom(PokemonDetailViewModel::class.java)) {
            return PokemonDetailViewModel(PokemonRepository(ApiService.pokemonService)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}