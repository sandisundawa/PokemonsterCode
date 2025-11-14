package id.codeid.pokemon.domain.repository

import id.codeid.pokemon.domain.model.Pokemon

interface PokeRepository {
    suspend fun fetchPokemonList(limit: Int = 10, offset: Int = 0): List<Pokemon>
}