package id.codeid.pokemon.presentation.home

import androidx.lifecycle.ViewModel
import id.codeid.pokemon.domain.model.Profile
import id.codeid.pokemon.domain.repository.ProfileRepository
import id.codeid.pokemon.domain.usecase.GetLoggedInProfileUseCase

class ProfileViewModel(
    private val getLoggedInProfileUseCase: GetLoggedInProfileUseCase
) {
    fun getProfile(): Profile {
        return getLoggedInProfileUseCase()
    }
}