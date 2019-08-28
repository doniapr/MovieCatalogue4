package com.doniapr.moviecatalogue4.favorite;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tbFavorite")
public class Favorite implements Serializable {
    @PrimaryKey()
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "rating")
    public float rating;

    @ColumnInfo(name = "poster")
    public String poster;

    @ColumnInfo(name = "isMovie")
    public int isMovie;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getIsMovie() {
        return isMovie;
    }

    public void setIsMovie(int isMovie) {
        this.isMovie = isMovie;
    }
}
