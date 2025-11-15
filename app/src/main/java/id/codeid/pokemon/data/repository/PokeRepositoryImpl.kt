package id.codeid.pokemon.data.repository

import com.google.gson.Gson
import id.codeid.pokemon.data.local.PokemonDao
import id.codeid.pokemon.data.local.PokemonDetailEntity
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
            // ambil id nya dari path terakhir dari url
            // bismillah gak ada yg sama
            id?.let { PokemonEntity(id = id, name = dto.name) }
        }

        dao.insertAll(entities)

        return entities.map { Pokemon(it.id, it.name) }
    }

    override suspend fun fetchPokemonDetail(name: String): PokemonDetailResponse {
        val gson = Gson()
        val local = dao.getDetail(name)
        if (local != null) {
            return gson.fromJson(local.json, PokemonDetailResponse::class.java)
        }
        val remote = api.getDetailPokemon(name)

        val json = gson.toJson(remote)
        dao.insert(PokemonDetailEntity(name = name, json = json))

        return remote
    }


}