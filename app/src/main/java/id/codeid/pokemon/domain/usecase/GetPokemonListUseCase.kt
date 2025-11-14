package id.codeid.pokemon.domain.usecase

import id.codeid.pokemon.domain.model.Pokemon
import id.codeid.pokemon.domain.repository.PokeRepository

class GetPokemonListUseCase(private val repository: PokeRepository) {
    suspend operator fun invoke(limit: Int, offset: Int): List<Pokemon> {
        return repository.fetchPokemonList(limit, offset)
    }
}