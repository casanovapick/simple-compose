package com.example.compse

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compse.pokemon.data.PokemonListResponse
import com.example.compse.pokemon.data.PokemonRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel (private val repository: PokemonRepository): ViewModel() {
    private val _pokemonList = MutableLiveData<List<PokemonListResponse.Result>>()
    val pokemonList: LiveData<List<PokemonListResponse.Result>> = _pokemonList
    fun getPokemonList(){
        if (_pokemonList.value == null) {
            repository.getPokemonList()
                .onEach {
                    _pokemonList.postValue(it)
                }
                .catch {
                    Log.e("MainViewModel", "getPokemonList", it)
                }
                .launchIn(viewModelScope)
        }
    }
}