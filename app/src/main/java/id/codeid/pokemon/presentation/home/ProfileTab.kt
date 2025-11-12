package id.codeid.pokemon.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import id.codeid.pokemon.data.local.SessionManager
import id.codeid.pokemon.data.repository.ProfileRepositoryImpl
import id.codeid.pokemon.domain.usecase.GetLoggedInProfileUseCase

@Composable
fun ProfileTab(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val viewModel = remember {
        val sessionManager = SessionManager(context)
        val repository = ProfileRepositoryImpl(sessionManager)
        val getLoggedInProfileUseCase = GetLoggedInProfileUseCase(repository)
        ProfileViewModel(getLoggedInProfileUseCase)
    }


    val profile = viewModel.getProfile()

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Selamat datang, ${profile.name}!",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Alamat kamu: ${profile.address}",
                style = MaterialTheme.typography.bodyLarge
            )

        }
    }
}
