package com.gprosper.stridetest.ui.pokemon

import androidx.lifecycle.*
import com.gprosper.stridetest.data.api.model.pokemon.PokemonDetail
import com.gprosper.stridetest.data.repo.PokemonRepository
import com.gprosper.stridetest.util.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class PokemonViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {

    private val _pokemon = MutableLiveData<Resource<List<PokemonDetail>>>()
    val pokemon: LiveData<Resource<List<PokemonDetail>>> = _pokemon

    private val _loadingMorePokemon = MutableLiveData<Boolean>()
    val loadingMorePokemon: LiveData<Boolean> = _loadingMorePokemon

    private val mutex = Mutex();
    private var backedPokemonList: List<PokemonDetail> = arrayListOf()
    private var offset = 0;
    private var canLoadMore = true

    init {
        getPokemon()
    }

    private fun getPokemon() = viewModelScope.launch {
        try {
            _pokemon.postValue(Resource.Loading())
            backedPokemonList = pokemonRepository.getPokemon()
                .also { canLoadMore = it.next != null }
                .results.map {
                    viewModelScope.async {
                        pokemonRepository.getPokemonDetail(it.name)
                    }
                }.awaitAll().sortedBy { it.id }
            _pokemon.postValue(Resource.Success(backedPokemonList))
        } catch (ex: Exception) {
            _pokemon.postValue(Resource.Error(ex.message ?: "Error Occurred"))
            throw ex
        }
    }

    fun loadMorePokemon() = viewModelScope.launch {
        if (!canLoadMore) return@launch
        if (mutex.isLocked) return@launch
        mutex.withLock {
            try {
                val newOffset = offset + 20;
                _loadingMorePokemon.postValue(true)

                val newPokemon = pokemonRepository.getPokemon(offset = newOffset)
                    .also { canLoadMore = it.next != null }
                    .results.map {
                        viewModelScope.async {
                            pokemonRepository.getPokemonDetail(it.name)
                        }
                    }.awaitAll()

                backedPokemonList = (backedPokemonList + newPokemon).sortedBy { it.id }
                offset = newOffset
                _pokemon.postValue(Resource.Success(backedPokemonList))
            } catch (ex: Exception) {
                _pokemon.postValue(Resource.Error(ex.message ?: "Error Occurred"))
            } finally {
                _loadingMorePokemon.postValue(false)
            }
        }
    }
}