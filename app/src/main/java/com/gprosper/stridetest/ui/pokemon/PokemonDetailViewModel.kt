package com.gprosper.stridetest.ui.pokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gprosper.stridetest.data.api.model.pokemon.PokemonDetail
import com.gprosper.stridetest.data.repo.PokemonRepository
import com.gprosper.stridetest.util.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class PokemonDetailViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {

    private val _pokemonDetails = MutableLiveData<Resource<PokemonDetail>>()
    val pokemonDetail: LiveData<Resource<PokemonDetail>> = _pokemonDetails

    fun loadPokemonDetails(name: String) = viewModelScope.launch {
        try {
            _pokemonDetails.postValue(Resource.Loading())
            val pokemonDetail = pokemonRepository.getPokemonDetail(name)
            _pokemonDetails.postValue(Resource.Success(pokemonDetail))
        } catch (ex: Exception) {
            _pokemonDetails.postValue(Resource.Error(ex.message ?: "Error Occurred"))
        }
    }
}