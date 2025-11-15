package id.codeid.pokemon.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.codeid.pokemon.domain.model.Pokemon
import id.codeid.pokemon.domain.usecase.GetPokemonListUseCase
import id.codeid.pokemon.domain.usecase.SearchPokemonUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val searchUseCase: SearchPokemonUseCase
) : ViewModel() {

    private val _pokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemonList = _pokemonList.asStateFlow()

    private var offset = 0
    private val limit = 10
    private var isLoading = false
    private var isSearchMode = false

    init {
        loadMorePokemon()
    }

    fun loadMorePokemon() {
        if (isSearchMode) return
        if (isLoading) return

        isLoading = true

        viewModelScope.launch {
            val newData = getPokemonListUseCase(limit, offset)
            _pokemonList.value += newData
            offset += limit
            isLoading = false
        }
    }

    fun searchPokemon(query: String) = viewModelScope.launch {

        if (query.isBlank()) {
            isSearchMode = false
            offset = 0
            _pokemonList.value = emptyList()
            loadMorePokemon()
            return@launch
        }

        isSearchMode = true

        val local = searchUseCase.searchLocal(query)
        if (local.isNotEmpty()) {
            val pokemonList = local.map { Pokemon(it.id, it.name) }
            _pokemonList.value = pokemonList
            return@launch
        }
    }

}
