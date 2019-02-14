package com.example.admin.tvshows.EpisodesBySeason;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.tvshows.Activities.Search;
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

        //searching intent
        handleIntent(getIntent());

        // get the text from MainActivity
        Intent intent = getIntent();
        String showID = intent.getExtras().getString("showID");

        String showMovieName = intent.getExtras().getString("showMovieName");
        TextView textNameMovie = (TextView) findViewById(R.id.movieName);
        textNameMovie.setText(showMovieName);

        String sessionNumber = "Season : " + intent.getExtras().getString("sessionNumber") + " |";
        TextView textSessionNumber = (TextView) findViewById(R.id.sessionNumber);
        textSessionNumber.setText(sessionNumber);

        //calling the method to display the episode of the Season
        getEpisodes(showID);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            //use the query to search your data somehow
            Intent searchActivity = new Intent(getApplicationContext(), Search.class);
            searchActivity.putExtra("term", query);
            startActivity(searchActivity);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
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
                if(episodesSeasonList == null || episodesSeasonList.size() == 0){
                    TextView notAvaiable = (TextView) findViewById(R.id.not_avaiable);
                    notAvaiable.setText("Not available from service");
                    TextView allEpisodes = (TextView) findViewById(R.id.episodeAll);
                    allEpisodes.setText("Episodes : 0");
                }else{
                    TextView allEpisodes = (TextView) findViewById(R.id.episodeAll);
                    String sizeList = "Episodes : " + Integer.toString(episodesSeasonList.size());
                    allEpisodes.setText(sizeList);
                }
                adapter.setEpisodesList(episodesSeasonList);
            }

            @Override
            public void onFailure(Call<List<EpisodesSeasonList>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
