package com.example.admin.tvshows.EpisodesBySeason;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.tvshows.Api;
import com.example.admin.tvshows.R;
import com.example.admin.tvshows.pogo.EpisodesSeasonList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EpisodesBySeason extends AppCompatActivity {

    List<EpisodesSeasonList> episodesSeasonList;
    RecyclerView recyclerView;
    EpisodesSeasonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes_by_season);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // get the text from MainActivity
        Intent intent = getIntent();
        String showID = intent.getExtras().getString("showID");

        String showMovieName = intent.getExtras().getString("showMovieName");
        TextView textNameMovie = (TextView) findViewById(R.id.movieName);
        textNameMovie.setText(showMovieName);

        String sessionNumber = "Season : " + intent.getExtras().getString("sessionNumber") + " | Episodes : 13";
        TextView textSessionNumber = (TextView) findViewById(R.id.sessionNumber);
        textSessionNumber.setText(sessionNumber);

        //calling the method to display the episode of the Season
        getEpisodes(showID);
    }

    private void getEpisodes(String showID) {
        episodesSeasonList = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.epSeason);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new EpisodesSeasonAdapter(getApplicationContext(), episodesSeasonList);
        recyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<EpisodesSeasonList>> call = api.getEpisodesSeason(showID);

        call.enqueue(new Callback<List<EpisodesSeasonList>>() {
            @Override
            public void onResponse(Call<List<EpisodesSeasonList>> call, Response<List<EpisodesSeasonList>> response) {
                episodesSeasonList = response.body();
                adapter.setEpisodesList(episodesSeasonList);
            }

            @Override
            public void onFailure(Call<List<EpisodesSeasonList>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
