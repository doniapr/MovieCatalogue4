package com.doniapr.moviecatalogue4.movie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieViewModel extends ViewModel {
    public static final String API_KEY = "e4891d0e71c90b3a33ba723876aaa030";
    private String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();

    void setMovie(Context context) {
        final ArrayList<Movie> list = new ArrayList<>();
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray responseArray = response.getJSONArray("results");
                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject movie = responseArray.getJSONObject(i);
                        Movie movieitem = new Movie(movie);
                        list.add(movieitem);
                    }
                    listMovies.postValue(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onFailure", error.getMessage());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(objectRequest);
    }

    LiveData<ArrayList<Movie>> getMovies() {
        return listMovies;
    }
}
