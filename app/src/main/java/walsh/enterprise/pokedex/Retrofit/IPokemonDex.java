package walsh.enterprise.pokedex.Retrofit;

import io.reactivex.Observable;
import retrofit2.http.GET;
import walsh.enterprise.pokedex.Model.Pokedex;

public interface IPokemonDex {
    @GET("pokedex.json")
    Observable<Pokedex> getListPokemon();

}
