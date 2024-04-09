@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.compse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withResumed
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.compse.pokemon.data.PokemonListResponse
import com.example.compse.pokemon.data.imageUrl
import com.example.compse.ui.theme.CompseTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val pokemonList by viewModel.pokemonList.observeAsState(emptyList())
            MainScreen(pokemonList)
        }
        lifecycleScope.launch {
            lifecycle.withResumed {
                viewModel.getPokemonList()
            }
        }
    }


}

@Preview
@Composable
private fun MainScreenPreview() {
    val pokemonList = mutableListOf<PokemonListResponse.Result>()
    (1..9).onEach {
        pokemonList.add(
            element = PokemonListResponse.Result(
                "Pokemon$it",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1/"
            )
        )
    }
    MainScreen(pokemonList = pokemonList)
}

@Composable
private fun MainScreen(pokemonList: List<PokemonListResponse.Result>) {
    CompseTheme {
        Scaffold(topBar = {
            TopAppBar(colors = topAppBarColors(
                containerColor = colorScheme.primary,
                titleContentColor = colorScheme.onPrimary,
            ), title = {
                Text("PokeDex")
            })
        }

        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                PokemonGridLayout(pokemonList)
            }

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PokemonGridLayout(pokemonList: List<PokemonListResponse.Result>) {
    LazyVerticalGrid(
        contentPadding = PaddingValues(8.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(pokemonList) {
            PokemonCard(it)
        }
    }
}


@Composable
private fun PokemonCard(pokemon: PokemonListResponse.Result) {

    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white))
    ) {
        var height by remember { mutableStateOf(Dp.Unspecified) }
        val density = LocalDensity.current
        val maxline = 2

        AsyncImage(
            modifier = Modifier.fillMaxWidth(1f),
            model  = ImageRequest.Builder(LocalContext.current)
                .data(pokemon.imageUrl())
                .crossfade(true)
                .build(),
            contentDescription = pokemon.name,
            placeholder = painterResource(R.drawable.ic_pokeball)
        )


        Divider(color = Color.Black, thickness = 1.dp)
        Text(
            text = pokemon.name.capitalize(Locale("en")),
            textAlign = TextAlign.Center,
            onTextLayout = { textLayoutResult ->
                val newHeight = with(density) { textLayoutResult.size.height.toDp() }
                if (textLayoutResult.lineCount == 1) {
                    height = newHeight * maxline
                }
            },
            overflow = TextOverflow.Ellipsis,
            maxLines = maxline,
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(height)
                .wrapContentHeight()
        )

    }
}