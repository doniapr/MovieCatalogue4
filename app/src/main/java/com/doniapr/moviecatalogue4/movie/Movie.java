package com.doniapr.moviecatalogue4.movie;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {
    private Integer id;
    private String title, poster;
    private float rating;

    public Movie(JSONObject object) {
        try {
            Integer id = object.getInt("id");
            String title = object.getString("title");
            String poster = "https://image.tmdb.org/t/p/w154" + object.getString("poster_path");
            double rating = object.getDouble("vote_average");

            this.id = id;
            this.title = title;
            this.poster = poster;
            this.rating = (float) rating;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
