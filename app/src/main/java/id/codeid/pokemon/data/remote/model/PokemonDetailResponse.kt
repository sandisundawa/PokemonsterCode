package id.codeid.pokemon.data.remote.model

data class PokemonDetailResponse(
    val abilities: List<AbilitySlot>? = emptyList(),
    val forms: List<Form>? = emptyList(),
    val held_items: List<HeldItemSlot>? = emptyList()
)