package id.codeid.pokemon.data.remote

import id.codeid.pokemon.data.remote.model.PokemonDetailResponse
import id.codeid.pokemon.data.remote.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse

    @GET("pokemon/{name}")
    suspend fun getDetailPokemon(
        @Path("name") name: String
    ) : PokemonDetailResponse
}