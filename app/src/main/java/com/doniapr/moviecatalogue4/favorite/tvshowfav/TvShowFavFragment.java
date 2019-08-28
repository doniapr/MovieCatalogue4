package com.doniapr.moviecatalogue4.favorite.tvshowfav;


import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doniapr.moviecatalogue4.R;
import com.doniapr.moviecatalogue4.database.AppDatabase;
import com.doniapr.moviecatalogue4.favorite.Favorite;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavFragment extends Fragment {
    TvShowFavAdapter adapter;
    ArrayList<Favorite> movieFav;
    RecyclerView recyclerView;
    private AppDatabase db;

    public TvShowFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = Room.databaseBuilder(getContext(), AppDatabase.class, "favoritedb").build();
        movieFav = new ArrayList<>();
        adapter = new TvShowFavAdapter();
        recyclerView = view.findViewById(R.id.rv_fav_tv_show);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        getTvShowFav();
    }

    @SuppressLint("StaticFieldLeak")
    private void getTvShowFav() {
        new AsyncTask<Void, Void, ArrayList<Favorite>>() {
            @Override
            protected ArrayList<Favorite> doInBackground(Void... voids) {
                movieFav = (ArrayList<Favorite>) db.favoriteDAO().selectTvShowFavorite();
                return movieFav;
            }

            @Override
            protected void onPostExecute(ArrayList<Favorite> result) {
                adapter.setFavoriteTv(result);
                adapter.notifyDataSetChanged();
                Log.d("movieData", String.valueOf(movieFav));
                recyclerView.setAdapter(adapter);
            }
        }.execute();
    }
}
