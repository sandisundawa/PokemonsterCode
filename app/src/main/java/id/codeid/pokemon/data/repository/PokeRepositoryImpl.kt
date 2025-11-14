package id.codeid.pokemon.data.repository

import id.codeid.pokemon.data.local.PokemonDao
import id.codeid.pokemon.data.local.PokemonEntity
import id.codeid.pokemon.data.remote.PokeApi
import id.codeid.pokemon.data.remote.model.PokemonDetailResponse
import id.codeid.pokemon.domain.model.Pokemon
import id.codeid.pokemon.domain.repository.PokeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokeRepositoryImpl(private val api: PokeApi, private val dao: PokemonDao) : PokeRepository {

    override suspend fun fetchPokemonList(limit: Int, offset: Int): List<Pokemon> {
        val localData = dao.getPokemonList(limit, offset)

        if (localData.isNotEmpty()) {
            return localData.map {
                Pokemon(id = it.id, name = it.name)
            }
        }

        val resp = api.getPokemonList(limit, offset)

        val entities = resp.results.mapNotNull { dto ->
            val id = dto.url.trimEnd('/').split("/").lastOrNull()?.toIntOrNull()
            id?.let { PokemonEntity(id = id, name = dto.name) }
        }

        dao.insertAll(entities)

        return entities.map { Pokemon(it.id, it.name) }
    }

    override suspend fun fetchPokemonDetail(name: String): PokemonDetailResponse {
        return withContext(Dispatchers.IO) {
            api.getDetailPokemon(name)
        }
    }


}