package com.example.compse.di

import com.example.compse.MainViewModel
import com.example.compse.pokemon.data.PokemonApi
import com.example.compse.pokemon.data.PokemonRepository
import com.example.compse.pokemon.data.RemotePokemonRepository
import com.example.compse.retrofit.RetrofitFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
    factory<PokemonRepository> { RemotePokemonRepository(get()) }
    factory { RetrofitFactory.createService(PokemonApi::class.java) }
}