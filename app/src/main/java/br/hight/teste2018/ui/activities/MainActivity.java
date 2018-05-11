package br.hight.teste2018.ui.activities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import br.hight.teste2018.R;
import br.hight.teste2018.network.RestClient;
import br.hight.teste2018.network.TMDbApi;
import br.hight.teste2018.network.responses.ApiResponse;
import br.hight.teste2018.network.responses.ResultsResponse;
import br.hight.teste2018.realm.RealmBitmap;
import br.hight.teste2018.realm.RealmInteger;
import br.hight.teste2018.realm.RealmMovie;
import br.hight.teste2018.ui.adapters.MoviesAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Thiago Azevedo on 27/04/2018.
 */
public class MainActivity extends AppCompatActivity {

    //SpeceNavigation Bar
    @BindView(R.id.space)
    SpaceNavigationView vSpaceNav;

    @BindView(R.id.nocontent)
    TextView vNoContent;

    @BindView(R.id.gridView)
    RecyclerView gridView;

    //adapter to show the Movie List
    private MoviesAdapter moviesAdapter;
    public Bitmap cover;
    public ArrayList<Bitmap> covers;
    public ArrayList<RealmMovie> movies;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bind views (with @BindView)
        ButterKnife.bind(this);

        movies = new ArrayList<>();
        covers = new ArrayList<>();
        id = 0;

        //initiate Realm
        Realm.init(this);

        //initiate SpaceNav
        initiateSpaceNav(savedInstanceState);

        searchForMovies();

    }

    /**
     * Search for movies on TMDbApi and sotre the results on database;
     */
    private void searchForMovies() {

        //create a Callback to save the results on retrofit class;

        Call<ApiResponse> call = RestClient.createService(TMDbApi.class).getMain();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, final Response<ApiResponse> response) {

                //If sucessful, store the results and show to the user, else, search for results on
                // database
                if(response.isSuccessful()){
                    saveMovies(response);
                }else{
                    showMovies();
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                showMovies();
            }
        });
    }

    /**
     * A Method to convert Integers in RealmIntegers (required to store the data)
     * @param genreIds, ArrayList with Integer ids
     * @return RealmList with RealmInteger ids
     */
    private RealmList<RealmInteger> convertIntegers(ArrayList<Integer> genreIds) {
        RealmList<RealmInteger> rl = new RealmList<>();
        for(Integer i : genreIds){
            RealmInteger ri = new RealmInteger();
            ri.setValue(i);
            rl.add(ri);
        }
        return rl;
    }

    /**
     *Method for save values from API
     * @param response, body from API response
     */
    private void saveMovies(Response<ApiResponse> response){
        Realm realm = Realm.getDefaultInstance();
        //Store the result for every ResultsResponse
        for(ResultsResponse r : response.body().getResults()){
            try {
                realm.beginTransaction();
                RealmMovie movie = new RealmMovie();

                movie.setId(r.getId());
                movie.setVoteCount(r.getVoteCount());
                movie.setVideo(r.getVideo());
                movie.setVoteAverage(movie.getVoteAverage());
                movie.setTitle(r.getTitle());
                movie.setPopularity(r.getPopularity());
                movie.setPosterPath(r.getPosterPath());
                movie.setOriginalLanguage(r.getOriginalLanguage());
                movie.setOriginalTitle(r.getOriginalTitle());
                movie.setGenreIds(convertIntegers(r.getGenreIds()));
                movie.setBackdropPath(r.getBackdropPath());
                movie.setAdult(r.getAdult());
                movie.setOverview(r.getOverview());
                movie.setReleaseDate(r.getReleaseDate());

                realm.copyToRealmOrUpdate(movie);
                realm.commitTransaction();

            }catch (Exception e) {
                e.printStackTrace();
                realm.cancelTransaction();
            }
        }

        showMovies();
    }

    /**
     * Method for retrieve the Bitmap from URL
     * @param src, movie poster path
     */

    public void getBitmapFromURL(String src) {
        String s = "https://image.tmdb.org/t/p/w500/".concat(src);
        ImageView img = new ImageView(this);
        URL url = null;
        try {
            url = new URL(s);
            GenerateBitmapTask task = new GenerateBitmapTask();
            task.execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * AsyncTask To create the Bitmaps from URL;
     */
    @SuppressLint("StaticFieldLeak")
    class GenerateBitmapTask extends AsyncTask<URL, Void, Bitmap> {

        Bitmap btm = null;

        @Override
        protected Bitmap doInBackground(URL... urls) {
            try {
                btm = BitmapFactory.decodeStream(urls[0].openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return btm;
        }

        protected void onPostExecute(Bitmap result) {
            Realm realm = Realm.getDefaultInstance();
            covers.add(result);
            try{
                realm.beginTransaction();
                RealmBitmap rb = new RealmBitmap();

                ByteArrayOutputStream blob = new ByteArrayOutputStream();
                result.compress(Bitmap.CompressFormat.PNG, 0 , blob);
                byte[] byteArray = blob.toByteArray();


                rb.setId(id);
                id++;
                rb.setPoster(byteArray);
                realm.copyToRealmOrUpdate(rb);
                realm.commitTransaction();

            }catch (Exception e){
                e.printStackTrace();
                realm.cancelTransaction();
            }

            //if we already have all the covers create the adapter;
            if(covers.size()== movies.size()){
                if(movies.size() == 0){
                    vNoContent.setVisibility(View.VISIBLE);
                }else{

                    //if theres covers to save, create a RealmClass to store them
                    RealmBitmap rb = new RealmBitmap();
                    for(int i = 0; i < rb.getSize(realm); i++){
                        rb = rb.getByIndex(realm, i);
                        Bitmap bitmap = Bitmap.createBitmap(BitmapFactory.decodeByteArray(rb.getPoster(), 0, rb.getPoster().length)) ;
                        try{
                            covers.set(i, bitmap);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    vNoContent.setVisibility(View.INVISIBLE);
                    gridView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
                    gridView.setLayoutManager(layoutManager);

                    moviesAdapter = new MoviesAdapter(movies, covers);
                    gridView.setAdapter(moviesAdapter);
                    gridView.invalidate();
                }
            }
        }
    }

    /**
     * Search on Database and show the results in a GridView
     */
    private void showMovies() {

        //create a new Thread to not use the UIThread;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Realm realm = Realm.getDefaultInstance();
                RealmMovie movie = new RealmMovie();
                for(int i = 0; i < movie.getSize(realm); i++){
                    movie = movie.getByIndex(realm, i);
                    getBitmapFromURL(movie.getPosterPath());
                    movies.add(movie);
                }
            }
        });
        t.run();
    }

    /**
     * Method that initite SpaceNavigationView;
     * It requires at least 2 SpaceItem, the right one and the left one
     */
    private void initiateSpaceNav(Bundle savedInstanceState) {
        vSpaceNav.initWithSaveInstanceState(savedInstanceState);
        vSpaceNav.addSpaceItem(new SpaceItem("", 0));
        vSpaceNav.addSpaceItem(new SpaceItem("Next", 0));
        vSpaceNav.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {

            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {

            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });
    }
}
