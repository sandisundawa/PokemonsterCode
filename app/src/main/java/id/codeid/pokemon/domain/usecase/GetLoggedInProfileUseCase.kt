package id.codeid.pokemon.domain.usecase

import id.codeid.pokemon.domain.model.Profile
import id.codeid.pokemon.domain.repository.ProfileRepository

class GetLoggedInProfileUseCase(
    private val repository: ProfileRepository
) {
    operator fun invoke(): Profile {
        return repository.getLoggedInProfile()
    }
}