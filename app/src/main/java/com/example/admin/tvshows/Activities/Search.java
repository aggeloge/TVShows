package com.example.admin.tvshows.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.tvshows.Adapters.SearchShowsAdapter;
import com.example.admin.tvshows.Api;
import com.example.admin.tvshows.R;
import com.example.admin.tvshows.pogo.SearchListShows;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Search extends AppCompatActivity
{

    List<SearchListShows> showsList;
    RecyclerView recyclerView;
    SearchShowsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        // get the search text from MainActivity
        String strSearchResult = "Search Result : ";
        String term =  intent.getExtras().getString("term");
        String txtSearch = strSearchResult + " " + term;
        TextView textAddSearch = (TextView) findViewById(R.id.searchText);
        textAddSearch.setText(txtSearch);

        //calling the method to display the shows
        getSearch(term);
    }

    private void getSearch(String term) {
        showsList = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.searchShows);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new SearchShowsAdapter(getApplicationContext(), showsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<SearchListShows>> call = api.getSearchShows(term);

        call.enqueue(new Callback<List<SearchListShows>>() {
            @Override
            public void onResponse(Call<List<SearchListShows>> call, Response<List<SearchListShows>> response) {
                showsList = response.body();
                adapter.setShowsSearch(showsList);
            }

            @Override
            public void onFailure(Call<List<SearchListShows>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
