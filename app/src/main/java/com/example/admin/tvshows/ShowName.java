package com.example.admin.tvshows;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.tvshows.Activities.Search;
import com.example.admin.tvshows.Cast.Cast;
import com.example.admin.tvshows.EpisodesBySeason.EpisodesBySeason;

public class ShowName extends AppCompatActivity {

    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get the text from MainActivity
        Intent intent = getIntent();

        //searching intent
        handleIntent(getIntent());

        // set the name of the episode
        final String showMovieName= intent.getExtras().getString("showMovieName");
        TextView textViewMovie = (TextView) findViewById(R.id.movieTile);
        textViewMovie.setText(showMovieName);

        // set the type of movie
        String typeOfMovie = intent.getExtras().getString("showType") + " | ";
        TextView typeMov = (TextView) findViewById(R.id.showType);
        typeMov.setText(typeOfMovie);

        // set the average
        if(intent.getExtras().getString("averageShow") != null){
            String average = intent.getExtras().getString("averageShow");
            ratingBar=(RatingBar)findViewById(R.id.ratingBar);
            Float ratingValue = Float.parseFloat(average);
            //float ratingValue = 3.5f;
            ratingBar.setRating(ratingValue);
        }

        final String numberOfSession;
        if(intent.getExtras().containsKey("numSession")){
            // set the season number
            String strSeason = "Season : ";
            String numSession = strSeason + intent.getExtras().getString("numSession") + " | ";
            TextView textSeason = (TextView) findViewById(R.id.movieSeason);
            textSeason.setText(numSession);
            numberOfSession = intent.getExtras().getString("numSession");
        }else{
            numberOfSession = "-";
        }

        // set the number of the episode
        if(intent.getExtras().containsKey("numEp")){
            String strNumberEp = "Episode : ";
            String numEp = strNumberEp + intent.getExtras().getString("numEp");
            TextView textEp = (TextView) findViewById(R.id.movieEp);
            textEp.setText(numEp);
        }

        // set the name of the episode
        if(intent.getExtras().containsKey("showEpisodeName")){
            String strEp = "Episode : ";
            String showEpName= strEp + intent.getExtras().getString("showEpisodeName");
            TextView textViewEp = (TextView) findViewById(R.id.episodeTitle);
            textViewEp.setText(showEpName);
        }

        // set the summary of episode
        String showSummary = stripHtml(intent.getExtras().getString("summary"));
        TextView textSummary = (TextView) findViewById(R.id.summary);
        textSummary.setText(showSummary);

        // set the image of the movie
        String imgUrl= intent.getExtras().getString("showImage");
        ImageView myImg=(ImageView)findViewById(R.id.avatarImageView);
        if(imgUrl != null){
            String httpsImgUrl = imgUrl.replace("http", "https");
            Glide.with(this).load(httpsImgUrl).into(myImg);
        }else{
            Glide.with(this).load(R.drawable.movie).into(myImg);
        }


        //get id of show
        final String showID = intent.getExtras().getString("idShow");

        //Show Episodes
        Button btnShowEpisodes = (Button) findViewById(R.id.buttonShowEpisodes);
        assert btnShowEpisodes != null;

        btnShowEpisodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent episodesActivity = new Intent(view.getContext(), EpisodesBySeason.class);
                episodesActivity.putExtra("showID", showID);
                episodesActivity.putExtra("showMovieName", showMovieName);
                episodesActivity.putExtra("sessionNumber", numberOfSession);
                startActivity(episodesActivity);
            }
        });

        //Show Cast
        Button btnShowCast = (Button) findViewById(R.id.buttonShowCast);
        assert btnShowCast != null;

        btnShowCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent episodesActivity = new Intent(view.getContext(), Cast.class);
                episodesActivity.putExtra("showID", showID);
                episodesActivity.putExtra("showMovieName", showMovieName);
                startActivity(episodesActivity);
            }
        });
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

    public String stripHtml(String html) {
        return Html.fromHtml(html).toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
