package com.example.compse.pokemon.data

import retrofit2.http.GET

interface PokemonApi {
    @GET("pokemon?limit=151&offset=0")
    suspend fun getPokemonList() : PokemonListResponse
}