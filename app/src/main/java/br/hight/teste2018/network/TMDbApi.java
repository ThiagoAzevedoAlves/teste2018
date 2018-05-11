package br.hight.teste2018.network;

import br.hight.teste2018.network.responses.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Thiago Azevedo on 28/04/2018.
 */

public interface TMDbApi {

    @GET("/3/discover/movie?api_key=3ec6cb849c930e97e99a6c7ce638c4d1&language=pt-BR&sort_by=popularity.desc&include_adult=false&page=1")
    Call<ApiResponse> getMain();

    @GET("/3/discover/movie?api_key={key}&language={language}&sort_by={popularity}&include_adult={adult}&page={page}")
    Call<ApiResponse> discover(@Path("key") String key, @Path("language") String lang, @Path("popularity") String pop, @Path("adult") Boolean adult, @Path("page") Integer page);


}
