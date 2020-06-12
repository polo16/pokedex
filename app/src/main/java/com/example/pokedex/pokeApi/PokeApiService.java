package com.example.pokedex.pokeApi;

import com.example.pokedex.models.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeApiService {


    @GET("pokemon")
    Call<PokemonResponse> getPokemonList(@Query("limit")int limit,@Query("offset") int offset);
}
