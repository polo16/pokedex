package com.example.pokedex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.pokedex.models.Pokemon;
import com.example.pokedex.models.PokemonResponse;
import com.example.pokedex.pokeApi.PokeApiService;
import com.example.pokedex.pokeApi.PokeInfo;
import com.example.pokedex.pokeApi.PokemonListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "POKEDEX";
    public Retrofit retrofit;

    private RecyclerView recyclerView;
    private PokemonListAdapter pokemonListAdapter;

    private int offset;
    private boolean suitableForCharge;

    public interface  RecyclerViewOnItemClickListener {

        void onClick(View v, int position);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        pokemonListAdapter = new PokemonListAdapter(this);
        recyclerView.setAdapter(pokemonListAdapter);
                recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (dy > 0) {
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                if (suitableForCharge) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        Log.i(TAG, " Llegamos al final.");

                        suitableForCharge = false;
                        offset += 20;
                        getData(offset);
                    }
                }
            }
            }
        });

        recyclerView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, "Pokemon", Toast.LENGTH_SHORT).show();
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        suitableForCharge = true;
        offset = 0;
        getData(offset);
    }

    public void showInfo(){
        Toast.makeText(this, "pokemon", Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent(MainActivity.this, PokeInfo.class);
//                    myIntent.putExtra("key", value); //Optional parameters
        MainActivity.this.startActivity(myIntent);
    }
    public void getData(int offset){
        PokeApiService service = retrofit.create(PokeApiService.class);
        Call<PokemonResponse> pokemonResponseCall = service.getPokemonList(20,offset);

        pokemonResponseCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                suitableForCharge = true;
                if (response.isSuccessful()){

                    PokemonResponse pokemonResponse = response.body();
                    ArrayList<Pokemon> listPokemons =  pokemonResponse.getResults();
                        pokemonListAdapter.addListOfPokemon(listPokemons);

                } else {
                    Log.e(TAG,"onResponce: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                suitableForCharge = true;
                Log.e(TAG,"onFailure: " + t.getMessage());
            }
        });
    }

}
