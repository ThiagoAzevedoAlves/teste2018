package br.hight.teste2018.ui.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.hight.teste2018.R;
import br.hight.teste2018.realm.RealmMovie;
import io.realm.Realm;

/**
 * Created by Thiago Azevedo on 28/04/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    ArrayList<RealmMovie> movies;
    ArrayList<Bitmap> covers;
    Realm realm;
    private RealmMovie movie;
    ViewHolder holder;

    public MoviesAdapter(ArrayList<RealmMovie> m, ArrayList<Bitmap> c) {

        realm = Realm.getDefaultInstance();
        this.movies = new ArrayList<>();
        this.covers = new ArrayList<>();
        this.movies.addAll(m);
        this.covers.addAll(c);
    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.front_layout, viewGroup, false);
        holder = new ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder viewHolder, int i) {
        holder = viewHolder;
        movie = movies.get(i);
        holder.nameView.setText(movie.getTitle());
        holder.posterView.setImageBitmap(covers.get(i));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameView;
        ImageView posterView;

        public ViewHolder(View view) {
            super(view);

            nameView = (TextView)view.findViewById(R.id.name);
            posterView = (ImageView) view.findViewById(R.id.picture);
        }
    }

}
