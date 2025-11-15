package id.codeid.pokemon.domain.repository

import id.codeid.pokemon.data.local.PokemonEntity
import id.codeid.pokemon.data.remote.model.PokemonDetailResponse
import id.codeid.pokemon.domain.model.Pokemon

interface PokeRepository {
    suspend fun fetchPokemonList(limit: Int = 10, offset: Int = 0): List<Pokemon>
    suspend fun fetchPokemonDetail(name: String): PokemonDetailResponse

    suspend fun searchPokemonLocal(query: String): List<PokemonEntity>
}