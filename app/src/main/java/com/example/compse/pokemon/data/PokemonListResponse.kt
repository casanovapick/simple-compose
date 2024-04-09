package com.example.compse.pokemon.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponse(
    @SerialName("count")
    val count: Int,
    @SerialName("results")
    val results: List<Result>
) {
    @Serializable
    data class Result(
        @SerialName("name")
        val name: String,
        @SerialName("url")
        val url: String
    )
}

fun PokemonListResponse.Result.id() = url.split("/")
    .run { get(lastIndex - 1) }

fun PokemonListResponse.Result.imageUrl() = "$OFFICIAL_ART_URL/${id()}.png"

const val OFFICIAL_ART_URL =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork"