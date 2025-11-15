package id.codeid.pokemon.domain.usecase

import id.codeid.pokemon.data.local.PokemonEntity
import id.codeid.pokemon.domain.repository.PokeRepository

class SearchPokemonUseCase(private val repo: PokeRepository) {

    suspend fun searchLocal(query: String) =
        repo.searchPokemonLocal(query)
}