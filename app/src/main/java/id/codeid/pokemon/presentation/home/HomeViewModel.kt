package id.codeid.pokemon.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.codeid.pokemon.domain.model.Pokemon
import id.codeid.pokemon.domain.usecase.GetPokemonListUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private val _pokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemonList = _pokemonList.asStateFlow()

    private var offset = 0
    private val limit = 10
    private var isLoading = false

    init {
        loadMorePokemon()
    }

    fun loadMorePokemon() {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            val newData = getPokemonListUseCase(limit, offset)
            _pokemonList.value += newData
            offset += limit
            isLoading = false
        }
    }
}
