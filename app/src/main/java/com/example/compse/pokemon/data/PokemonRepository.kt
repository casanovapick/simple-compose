package com.example.compse.pokemon.data

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface PokemonRepository {
    fun getPokemonList() : Flow<List<PokemonListResponse.Result>>
}