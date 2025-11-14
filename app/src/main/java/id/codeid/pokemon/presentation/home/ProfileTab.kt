package id.codeid.pokemon.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.codeid.pokemon.data.local.SessionManager
import id.codeid.pokemon.data.repository.ProfileRepositoryImpl
import id.codeid.pokemon.domain.usecase.GetLoggedInProfileUseCase
import id.codeid.pokemon.ui.theme.PokemonsterCodeTheme

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

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Nama :",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = profile.name,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.End
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Alamat :",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = profile.address,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    PokemonsterCodeTheme {
        ProfileTab()
    }
}
