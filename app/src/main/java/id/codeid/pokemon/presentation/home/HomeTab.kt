package id.codeid.pokemon.presentation.home

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.codeid.pokemon.data.local.AppDatabase
import id.codeid.pokemon.data.local.PokemonDao
import id.codeid.pokemon.data.remote.ApiClient
import id.codeid.pokemon.data.repository.PokeRepositoryImpl
import id.codeid.pokemon.domain.usecase.GetPokemonListUseCase
import id.codeid.pokemon.presentation.detailscreen.DetailScreenActivity
import kotlinx.coroutines.flow.collect


@Composable
fun HomeTab(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val viewModel = remember {
        val api = ApiClient.pokeApi
        val db = AppDatabase.getDatabase(context)
        val dao = db.pokemonDao()
        val repository = PokeRepositoryImpl(api, dao)
        val useCase = GetPokemonListUseCase(repository)

        HomeViewModel(useCase)
    }

    val pokemonList by viewModel.pokemonList.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.collect { lastVisible ->
            val total = listState.layoutInfo.totalItemsCount
            if (lastVisible != null && lastVisible >= total - 1) {
                viewModel.loadMorePokemon()
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            modifier = Modifier.fillMaxWidth().padding(0.dp, 36.dp, 16.dp, 0.dp),
            text = "List Pokemon",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
        )

        LazyColumn(state = listState) {
            items(pokemonList) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(8.dp)
                )
                {

                    TextButton(onClick = {
                        val intent = Intent(context, DetailScreenActivity::class.java)
                        intent.putExtra("pokemonName", item.name)
                        context.startActivity(intent)
                    }) {

                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "${item.id} - ${item.name}",
                                modifier = Modifier.padding(16.dp)
                            )

                            Text(
                                text = "Lihat Detail",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }

            }
        }
    }
}
