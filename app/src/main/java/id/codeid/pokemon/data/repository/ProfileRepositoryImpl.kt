package id.codeid.pokemon.data.repository

import id.codeid.pokemon.data.local.SessionManager
import id.codeid.pokemon.domain.model.Profile
import id.codeid.pokemon.domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val sessionManager: SessionManager
) : ProfileRepository {

    override fun getLoggedInProfile(): Profile {

        val name = sessionManager.getUserName() ?: ""
        val address = sessionManager.getUserAddress() ?: ""

        return Profile(
            name = name,
            address = address
        )
    }
}