package id.codeid.pokemon.presentation.detailscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.codeid.pokemon.data.local.SessionManager
import id.codeid.pokemon.data.remote.ApiClient
import id.codeid.pokemon.data.repository.PokeRepositoryImpl
import id.codeid.pokemon.data.repository.ProfileRepositoryImpl
import id.codeid.pokemon.domain.usecase.GetLoggedInProfileUseCase
import id.codeid.pokemon.domain.usecase.GetPokemonDetailUseCase
import id.codeid.pokemon.presentation.home.HomeScreen
import id.codeid.pokemon.presentation.home.ProfileViewModel
import id.codeid.pokemon.ui.theme.PokemonsterCodeTheme


class DetailScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val pokemonName = intent.getStringExtra("pokemonName")
        setContent {
            PokemonsterCodeTheme(false) {
                DetailScreen(modifier = Modifier, pokemonName)
            }
        }
    }
}

@Composable
fun DetailScreen(modifier: Modifier = Modifier, pokemonName: String? = null) {
    val context = LocalContext.current

    val viewModel = remember {
        val api = ApiClient.pokeApi
        val repository = PokeRepositoryImpl(api)
        val getPokemonDetailUseCase = GetPokemonDetailUseCase(repository)
        DetailScreenViewModel(getPokemonDetailUseCase)
    }

    viewModel.loadDetailPokemon(pokemonName ?: "")
    val detailPokemon by viewModel.pokemonDetail.collectAsState()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp, 32.dp, 16.dp, 16.dp)
                .fillMaxSize()
        ) {

            Text(
                text = "Detail Pokemon $pokemonName",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp)
            )
            {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Ability $pokemonName",
                        modifier = Modifier.padding(16.dp),
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    LazyColumn {
                        items(detailPokemon.abilities?.size ?: 0) {
                            Text(
                                text = detailPokemon.abilities?.get(it)?.ability?.name ?: "",
                                color = Color.DarkGray,
                                modifier = Modifier.padding(16.dp, 4.dp, 4.dp, 4.dp),
                                fontSize = MaterialTheme.typography.bodySmall.fontSize
                            )
                        }
                    }

                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp)
            )
            {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Form Evolution $pokemonName",
                        modifier = Modifier.padding(16.dp),
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    LazyColumn {
                        items(detailPokemon.forms?.size ?: 0) {
                            Text(
                                text = detailPokemon.forms?.get(it)?.name ?: "",
                                color = Color.DarkGray,
                                modifier = Modifier.padding(16.dp, 4.dp, 4.dp, 4.dp),
                                fontSize = MaterialTheme.typography.bodySmall.fontSize
                            )
                        }
                    }

                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp)
            )
            {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Item for $pokemonName",
                        modifier = Modifier.padding(16.dp),
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    LazyColumn {
                        items(detailPokemon.held_items?.size ?: 0) {
                            Text(
                                text = detailPokemon.held_items?.get(it)?.item?.name ?: "",
                                modifier = Modifier.padding(16.dp, 4.dp, 4.dp, 4.dp),
                                color = Color.DarkGray,
                                fontSize = MaterialTheme.typography.bodySmall.fontSize
                            )
                        }
                    }

                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    PokemonsterCodeTheme {
        DetailScreen()
    }
}