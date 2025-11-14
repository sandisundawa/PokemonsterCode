package id.codeid.pokemon.domain.usecase

import id.codeid.pokemon.data.remote.model.PokemonDetailResponse
import id.codeid.pokemon.domain.model.Pokemon
import id.codeid.pokemon.domain.repository.PokeRepository

class GetPokemonDetailUseCase(private val repository: PokeRepository) {
    suspend operator fun invoke(name: String): PokemonDetailResponse {
        return repository.fetchPokemonDetail(name)
    }
}