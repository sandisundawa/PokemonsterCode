package id.codeid.pokemon.data.repository

import id.codeid.pokemon.data.remote.PokeApi
import id.codeid.pokemon.data.remote.model.PokemonDetailResponse
import id.codeid.pokemon.domain.model.Pokemon
import id.codeid.pokemon.domain.repository.PokeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokeRepositoryImpl(private val api: PokeApi) : PokeRepository {

    override suspend fun fetchPokemonList(limit: Int, offset: Int): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            val resp = api.getPokemonList(limit, offset)
            resp.results.mapNotNull { dto ->
                val id = dto.url
                    .trimEnd('/')
                    .split("/")
                    .lastOrNull()
                    ?.toIntOrNull()

                id?.let {
                    Pokemon(
                        id = it,
                        name = dto.name
                    )
                }
            }
        }
    }

    override suspend fun fetchPokemonDetail(name: String): PokemonDetailResponse {
        return withContext(Dispatchers.IO) {
            api.getDetailPokemon(name)
        }
    }


}