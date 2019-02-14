package com.example.admin.tvshows.Cast;

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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cast extends AppCompatActivity {

    List<CastList> castList;
    RecyclerView recyclerView;
    CastAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //searching intent
        handleIntent(getIntent());

        // get the text from MainActivity
        Intent intent = getIntent();
        String showID = intent.getExtras().getString("showID");

        String showMovieName = intent.getExtras().getString("showMovieName") + " - Cast";
        TextView textNameMovie = (TextView) findViewById(R.id.movieName);
        textNameMovie.setText(showMovieName);

        //calling the method to display the episode of the Season
        getCast(showID);
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

    private void getCast(String showID) {
        castList = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.listCast);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new CastAdapter(getApplicationContext(), castList);
        recyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<CastList>> call = api.getCast(showID);

        call.enqueue(new Callback<List<CastList>>() {
            @Override
            public void onResponse(Call<List<CastList>> call, Response<List<CastList>> response) {
                castList = response.body();
                if(castList == null || castList.size() == 0){
                    TextView notAvailable = (TextView) findViewById(R.id.not_avaiable);
                    notAvailable.setText("Not available from service");
                }
                adapter.setCastList(castList);
            }

            @Override
            public void onFailure(Call<List<CastList>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
