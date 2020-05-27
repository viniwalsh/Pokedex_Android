package walsh.enterprise.pokedex;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

import walsh.enterprise.pokedex.Adapter.PokemonEvolutionAdapter;
import walsh.enterprise.pokedex.Adapter.PokemonTypeAdapter;
import walsh.enterprise.pokedex.Common.Common;
import walsh.enterprise.pokedex.Model.Pokemon;


/**
 * A simple {@link Fragment} subclass.
 */
public class PokemonDetail extends Fragment {

    ImageView pokemon_img;
    TextView pokemon_name, pokemon_height, pokemon_weight;
    RecyclerView recycler_type, recycler_weakness, recycler_next_evolution, recycler_prev_evolution;

    private static  PokemonDetail instance;

    public static PokemonDetail getInstance() {
        if (instance == null)
            instance = new PokemonDetail();
        return instance;
    }

    public PokemonDetail() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_pokemon_details, container, false);

        Pokemon pokemon;
        //Get position from Argument
        if (getArguments().get("num") == null)
            pokemon = Common.commonPokemonList.get(getArguments().getInt("position"));
        else
            pokemon = Common.findPokemonByNum(getArguments().getString("num"));

        pokemon_img = itemView.findViewById(R.id.pokemon_image);
        pokemon_name = itemView.findViewById(R.id.name);
        pokemon_img = itemView.findViewById(R.id.pokemon_image);
        pokemon_height = itemView.findViewById(R.id.height);
        pokemon_weight = itemView.findViewById(R.id.weight);

        recycler_type = itemView.findViewById(R.id.recycler_type);
        recycler_type.setHasFixedSize(true);
        recycler_type.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recycler_weakness = itemView.findViewById(R.id.recycler_weakness);
        recycler_weakness.setHasFixedSize(true);
        recycler_weakness.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recycler_next_evolution = itemView.findViewById(R.id.recycler_next_evolution);
        recycler_next_evolution.setHasFixedSize(true);
        recycler_next_evolution.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recycler_prev_evolution = itemView.findViewById(R.id.recycler_prev_evolution);
        recycler_prev_evolution.setHasFixedSize(true);
        recycler_prev_evolution.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        setDetailPokemon(pokemon);
        
        return itemView;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    private void setDetailPokemon(Pokemon pokemon) {
        //Load image
        Glide.with(Objects.requireNonNull(getActivity())).load(pokemon.getImg()).into(pokemon_img);
        pokemon_name.setText(pokemon.getName());
        pokemon_weight.setText("Altura: " + pokemon.getWeight());
        pokemon_height.setText("Peso: " + pokemon.getHeight());

        //Set Type
        PokemonTypeAdapter typeAdapter = new PokemonTypeAdapter(getActivity(), pokemon.getType());
        recycler_type.setAdapter(typeAdapter);

        //Set Weakness
        PokemonTypeAdapter weaknessAdapter = new PokemonTypeAdapter(getActivity(), pokemon.getWeaknesses());
        recycler_weakness.setAdapter(weaknessAdapter);

        //Set Evolution
        PokemonEvolutionAdapter prevEvolutionAdapter = new PokemonEvolutionAdapter(getActivity(), pokemon.getPrev_evolution());
        recycler_prev_evolution.setAdapter(prevEvolutionAdapter);

        PokemonEvolutionAdapter nextEvolutionAdapter = new PokemonEvolutionAdapter(getActivity(), pokemon.getNext_evolution());
        recycler_next_evolution.setAdapter(nextEvolutionAdapter);
    }
}
