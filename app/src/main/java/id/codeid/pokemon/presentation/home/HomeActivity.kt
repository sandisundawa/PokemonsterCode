package id.codeid.pokemon.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import id.codeid.pokemon.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import id.codeid.pokemon.ui.theme.PokemonsterCodeTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonsterCodeTheme(false) {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    var selectedTab by remember { mutableStateOf("home") }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == "home",
                    onClick = { selectedTab = "home" },
                    icon = { Icon(painterResource(id = R.drawable.ic_home), contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = selectedTab == "profile",
                    onClick = { selectedTab = "profile" },
                    icon = { Icon(painterResource(id = R.drawable.ic_profile), contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            "home" -> HomeTab(Modifier.padding(innerPadding))
            "profile" -> ProfileTab(Modifier.padding(innerPadding))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    PokemonsterCodeTheme {
        HomeScreen()
    }
}