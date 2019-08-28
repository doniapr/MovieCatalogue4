package com.doniapr.moviecatalogue4.detail;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.doniapr.moviecatalogue4.R;
import com.doniapr.moviecatalogue4.database.AppDatabase;
import com.doniapr.moviecatalogue4.favorite.Favorite;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_TYPE = "extra_type";
    public static final String EXTRA_ID = "extra_id";
    ProgressBar progressBar;
    DetailViewModel detailViewModel;
    TextView txtTitle, txtOverview, txtReleaseDate, txtPopularity, txtRuntime;
    ImageView imgPoster;
    RatingBar ratingBar;
    Favorite favorite;
    boolean isMovie, isFavorite;
    private AppDatabase db;
    private Observer<ArrayList<Detail>> getDetail = new Observer<ArrayList<Detail>>() {
        @Override
        public void onChanged(@Nullable final ArrayList<Detail> detail) {
            if (detail != null) {
                txtTitle.setText(detail.get(0).getTitle());
                txtOverview.setText(detail.get(0).getOverview());
                txtReleaseDate.setText(detail.get(0).getRelease_date());
                txtPopularity.setText(detail.get(0).getPopularity());
                txtRuntime.setText(detail.get(0).getRuntime());
                Glide.with(DetailActivity.this).load(detail.get(0).getPoster())
                        .into(imgPoster);
                final float value = (detail.get(0).getRating() / 10) * 5;
                ratingBar.setRating(value);
                ratingBar.setIsIndicator(true);

                favorite = new Favorite();
                favorite.setId(detail.get(0).getId());
                favorite.setTitle(detail.get(0).getTitle());
                favorite.setPoster(detail.get(0).getPoster());
                favorite.setRating(value);
                if (isMovie) {
                    favorite.setIsMovie(1);
                } else {
                    favorite.setIsMovie(2);
                }
                showLoading(false);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();

        int id = getIntent().getIntExtra(EXTRA_ID, 0);
        isMovie = getIntent().getBooleanExtra(EXTRA_TYPE, true);

        checkIsFav(id);
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        detailViewModel.getDetail().observe(this, getDetail);
        detailViewModel.setDetail(this, isMovie, id);
        showLoading(true);
    }

    private void init() {
        progressBar = findViewById(R.id.progress_bar_detail);
        txtTitle = findViewById(R.id.txt_title_detail);
        txtOverview = findViewById(R.id.txt_content_overview);
        txtReleaseDate = findViewById(R.id.txt_content_releasedate);
        txtPopularity = findViewById(R.id.txt_content_popularity);
        txtRuntime = findViewById(R.id.txt_content_runtime);
        imgPoster = findViewById(R.id.img_poster_detail);
        ratingBar = findViewById(R.id.rating_detail);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "favoritedb").build();
    }

    @SuppressLint("StaticFieldLeak")
    private void insertData(final Favorite favorite) {
        new AsyncTask<Void, Void, Long>() {

            @Override
            protected Long doInBackground(Void... voids) {
                long status = db.favoriteDAO().insertFavorite(favorite);
                Log.d("status_db", String.valueOf(status));
                return status;
            }

            @Override
            protected void onPostExecute(Long aLong) {
                isFavorite = true;
                Toast.makeText(getApplicationContext(), "Berhasil Menambahkan Favorite", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void checkIsFav(final int id) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                String checkIsFav = db.favoriteDAO().checkIsFavorit(id);
                isFavorite = checkIsFav != null;
                return isFavorite;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void deleteFav(final int id) {
        new AsyncTask<Void, Void, Integer>() {

            @Override
            protected Integer doInBackground(Void... voids) {
                int status = db.favoriteDAO().deleteFavorite(id);
                return status;
            }

            @Override
            protected void onPostExecute(Integer aInteger) {
                isFavorite = false;
                Toast.makeText(getApplicationContext(), "Berhasil Menghapus Favorite", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        if (isFavorite) {
            menu.getItem(0).setIcon(R.drawable.ic_favorite_red_24dp);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_favorite) {
            if (isFavorite) {
                deleteFav(favorite.getId());
                item.setIcon(R.drawable.ic_favorite_white_24dp);
            } else {
                insertData(favorite);
                item.setIcon(R.drawable.ic_favorite_red_24dp);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

