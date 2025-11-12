package id.codeid.pokemon.domain.repository

import id.codeid.pokemon.domain.model.Profile

interface ProfileRepository {
    fun getLoggedInProfile(): Profile
}