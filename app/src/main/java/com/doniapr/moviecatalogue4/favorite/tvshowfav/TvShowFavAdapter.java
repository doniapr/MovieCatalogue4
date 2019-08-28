package com.doniapr.moviecatalogue4.favorite.tvshowfav;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

public class TvShowFavAdapter extends RecyclerView.Adapter<TvShowFavAdapter.TvShowFavViewHolder> {
    private ArrayList<Favorite> tvShowFav = new ArrayList<>();

    public void setFavoriteTv(ArrayList<Favorite> items) {
        tvShowFav.clear();
        tvShowFav.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowFavViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new TvShowFavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowFavViewHolder tvShowFavViewHolder, int i) {
        tvShowFavViewHolder.txtTitle.setText(tvShowFav.get(i).getTitle());
        tvShowFavViewHolder.txtTitle.setText(tvShowFav.get(i).getTitle());
        tvShowFavViewHolder.rating.setRating(tvShowFav.get(i).getRating());
        tvShowFavViewHolder.rating.setIsIndicator(true);
        Glide.with(tvShowFavViewHolder.itemView.getContext())
                .load(tvShowFav.get(i).getPoster())
                .into(tvShowFavViewHolder.imgPoster);
        tvShowFavViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tvShowFavViewHolder.itemView.getContext(), DetailActivity.class);
                intent.putExtra(EXTRA_ID, tvShowFav.get(tvShowFavViewHolder.getAdapterPosition()).getId());
                intent.putExtra(EXTRA_TYPE, false);
                tvShowFavViewHolder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvShowFav.size();
    }

    public class TvShowFavViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        RatingBar rating;
        ImageView imgPoster;

        public TvShowFavViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            rating = itemView.findViewById(R.id.rating);
            imgPoster = itemView.findViewById(R.id.img_poster);
        }
    }
}
