package com.doniapr.moviecatalogue4.favorite.moviefav;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doniapr.moviecatalogue4.R;
import com.doniapr.moviecatalogue4.detail.DetailActivity;
import com.doniapr.moviecatalogue4.favorite.Favorite;

import java.util.ArrayList;

import static com.doniapr.moviecatalogue4.detail.DetailActivity.EXTRA_ID;
import static com.doniapr.moviecatalogue4.detail.DetailActivity.EXTRA_TYPE;

public class MovieFavAdapter extends RecyclerView.Adapter<MovieFavAdapter.MovieFavViewHolder> {
    private ArrayList<Favorite> movieFavorites = new ArrayList<>();

    public void setFavorites(ArrayList<Favorite> movieFav) {
        movieFavorites.clear();
        movieFavorites.addAll(movieFav);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieFavViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new MovieFavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieFavViewHolder movieFavViewHolder, int i) {
        movieFavViewHolder.txtTitle.setText(movieFavorites.get(i).getTitle());
        movieFavViewHolder.txtTitle.setText(movieFavorites.get(i).getTitle());
        movieFavViewHolder.rating.setRating(movieFavorites.get(i).getRating());
        movieFavViewHolder.rating.setIsIndicator(true);
        Glide.with(movieFavViewHolder.itemView.getContext())
                .load(movieFavorites.get(i).getPoster())
                .into(movieFavViewHolder.imgPoster);
        movieFavViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(movieFavViewHolder.itemView.getContext(), DetailActivity.class);
                intent.putExtra(EXTRA_ID, movieFavorites.get(movieFavViewHolder.getAdapterPosition()).getId());
                Log.e("idnyaMovie", String.valueOf(movieFavorites.get(movieFavViewHolder.getAdapterPosition()).getId()));
                intent.putExtra(EXTRA_TYPE, true);
                movieFavViewHolder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieFavorites.size();
    }

    public class MovieFavViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        RatingBar rating;
        ImageView imgPoster;

        public MovieFavViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            rating = itemView.findViewById(R.id.rating);
            imgPoster = itemView.findViewById(R.id.img_poster);
        }
    }
}
