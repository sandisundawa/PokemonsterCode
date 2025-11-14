package id.codeid.pokemon.presentation.detailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.codeid.pokemon.data.remote.model.PokemonDetailResponse
import id.codeid.pokemon.domain.model.Pokemon
import id.codeid.pokemon.domain.usecase.GetPokemonDetailUseCase
import id.codeid.pokemon.domain.usecase.GetPokemonListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailScreenViewModel(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : ViewModel() {

    private val _pokemonDetail = MutableStateFlow(PokemonDetailResponse())
    val pokemonDetail = _pokemonDetail.asStateFlow()

    private var isLoading = false

    fun loadDetailPokemon(name: String) {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            val data = getPokemonDetailUseCase(name)
            _pokemonDetail.value = data
            isLoading = false
        }
    }
}