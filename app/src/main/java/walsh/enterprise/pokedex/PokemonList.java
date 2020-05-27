package walsh.enterprise.pokedex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import walsh.enterprise.pokedex.Adapter.PokemonListAdapter;
import walsh.enterprise.pokedex.Common.Common;
import walsh.enterprise.pokedex.Common.ItemOffsetDecoration;
import walsh.enterprise.pokedex.Model.Pokedex;
import walsh.enterprise.pokedex.Retrofit.IPokemonDex;
import walsh.enterprise.pokedex.Retrofit.RetrofitClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class PokemonList extends Fragment {

    IPokemonDex iPokemonDex;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    RecyclerView pokemon_list_recycleview;

    static PokemonList instance;

    public static PokemonList getInstance(){
        if(instance == null)
            instance = new PokemonList();
        return instance;
    }

    public PokemonList() {
        Retrofit retrofit = RetrofitClient.getInstace();
        iPokemonDex = retrofit.create(IPokemonDex.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);

        pokemon_list_recycleview = view.findViewById(R.id.pokemon_list_recyclerview);
        pokemon_list_recycleview.setHasFixedSize(true);
        pokemon_list_recycleview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.spacing);
        pokemon_list_recycleview.addItemDecoration(itemOffsetDecoration);

        fetchData();

        return view;
    }

    private void fetchData() {
        compositeDisposable.add(iPokemonDex.getListPokemon()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Pokedex>() {
                    @Override
                    public void accept(Pokedex pokedex) throws Exception {
                        Common.commonPokemonList = pokedex.getPokemon();
                        PokemonListAdapter adapter = new PokemonListAdapter(getActivity(), Common.commonPokemonList);
                        pokemon_list_recycleview.setAdapter(adapter);
                    }
                })
        );
    }
}
