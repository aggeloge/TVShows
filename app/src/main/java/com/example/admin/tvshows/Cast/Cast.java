package com.example.admin.tvshows.Cast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

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

        // get the text from MainActivity
        Intent intent = getIntent();
        String showID = intent.getExtras().getString("showID");

        String showMovieName = intent.getExtras().getString("showMovieName") + " - Cast";
        TextView textNameMovie = (TextView) findViewById(R.id.movieName);
        textNameMovie.setText(showMovieName);

        //calling the method to display the episode of the Season
        getCast(showID);
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
                adapter.setCastList(castList);
            }

            @Override
            public void onFailure(Call<List<CastList>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
