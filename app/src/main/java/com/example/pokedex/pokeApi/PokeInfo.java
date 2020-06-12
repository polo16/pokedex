package com.example.pokedex.pokeApi;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokedex.R;

public class PokeInfo extends AppCompatActivity {

    private ImageView fotoImageView;
    private TextView nombreTextView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poke_info_view);

        fotoImageView = findViewById(R.id.fotoImageView);
        nombreTextView = findViewById(R.id.nombreTextView);


    }

    public void setImage (){
        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + 25 + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
//                .into(holder.fotoImageView);
    }
}
