package com.example.compse.pokemon.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class RemotePokemonRepository(private val pokemonApi: PokemonApi) : PokemonRepository {
    override fun getPokemonList(): Flow<List<PokemonListResponse.Result>> = flow {
        emit(pokemonApi.getPokemonList().results)
    }.flowOn(Dispatchers.IO)
}