package com.doniapr.moviecatalogue4.detail;

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

import org.json.JSONObject;

import java.util.ArrayList;

import static com.doniapr.moviecatalogue4.movie.MovieViewModel.API_KEY;

public class DetailViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Detail>> detailData = new MutableLiveData<>();

    void setDetail(Context context, boolean isMovie, int id) {
        final ArrayList<Detail> details = new ArrayList<>();
        if (isMovie) {
            String url_movie = "https://api.themoviedb.org/3/movie/" + id + "?api_key=" + API_KEY + "&language=en-US";
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url_movie, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Detail item = new Detail(response, true);
                    details.add(item);
                    detailData.postValue(details);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("onFailure", error.getMessage());
                }
            });

            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(objectRequest);
        } else {
            String url_tvshow = "https://api.themoviedb.org/3/tv/" + id + "?api_key=" + API_KEY + "&language=en-US";
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url_tvshow, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Detail item = new Detail(response, false);
                    details.add(item);
                    detailData.postValue(details);
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
    }

    LiveData<ArrayList<Detail>> getDetail() {
        return detailData;
    }
}
