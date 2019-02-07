package com.example.admin.tvshows.Episodes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.tvshows.Api;
import com.example.admin.tvshows.R;
import com.example.admin.tvshows.pogo.EpisodesList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Episodes extends AppCompatActivity {

    List<EpisodesList> episodesList;
    RecyclerView recyclerView;
    EpisodesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        // get the text from MainActivity
        Intent intent = getIntent();
        String showID = intent.getExtras().getString("showID");

        String showMovieName = intent.getExtras().getString("showMovieName");
        TextView textNameMovie = (TextView) findViewById(R.id.movieTitle);
        textNameMovie.setText(showMovieName);

        //calling the method to display the seasons of show shows
        getEpisodes(showID,showMovieName);
    }

    private void getEpisodes(String showID,String showMovieName) {
        episodesList = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.listViewEpisodes);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new EpisodesAdapter(getApplicationContext(), episodesList, showMovieName, showID);
        recyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<EpisodesList>> call = api.getEpisodes(showID);

        call.enqueue(new Callback<List<EpisodesList>>() {
            @Override
            public void onResponse(Call<List<EpisodesList>> call, Response<List<EpisodesList>> response) {
                episodesList = response.body();
                adapter.setEpisodesList(episodesList);
            }

            @Override
            public void onFailure(Call<List<EpisodesList>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
