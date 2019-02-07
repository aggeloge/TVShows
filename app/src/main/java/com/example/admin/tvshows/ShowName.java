package com.example.admin.tvshows;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

        // set the name of the episode
        final String showMovieName= intent.getExtras().getString("showMovieName");
        TextView textViewMovie = (TextView) findViewById(R.id.movieTile);
        textViewMovie.setText(showMovieName);

        // set the type of movie
        String typeOfMovie = intent.getExtras().getString("showType") + " | ";
        TextView typeMov = (TextView) findViewById(R.id.showType);
        typeMov.setText(typeOfMovie);

        // set the average
        String average = intent.getExtras().getString("averageShow");
        //TextView avgMov = (TextView) findViewById(R.id.rating);
        //avgMov.setText(average);
        ratingBar=(RatingBar)findViewById(R.id.ratingBar);
//        ratingBar.setRating(Float.parseFloat(average));
//        ratingBar = ((RatingBar) findViewById(R.id.ratingBar));
        ratingBar.setRating(7);

        // set the season number
        String strSeason = "Season : ";
        String numSession = strSeason + intent.getExtras().getString("numSession") + " | ";
        TextView textSeason = (TextView) findViewById(R.id.movieSeason);
        textSeason.setText(numSession);

        final String numberOfSession = intent.getExtras().getString("numSession");

        // set the number of the episode
        String strNumberEp = "Episode : ";
        String numEp = strNumberEp + intent.getExtras().getString("numEp");
        TextView textEp = (TextView) findViewById(R.id.movieEp);
        textEp.setText(numEp);

        // set the name of the episode
        String strEp = "Episode : ";
        String showEpName= strEp + intent.getExtras().getString("showEpisodeName");
        TextView textViewEp = (TextView) findViewById(R.id.episodeTitle);
        textViewEp.setText(showEpName);

        // set the summary of episode
        String showSummary = stripHtml(intent.getExtras().getString("summary"));
        TextView textSummary = (TextView) findViewById(R.id.summary);
        textSummary.setText(showSummary);

        // set the image of the movie
        String imgUrl= intent.getExtras().getString("showImage");
        String httpsImgUrl = imgUrl.replace("http", "https");
        ImageView myImg=(ImageView)findViewById(R.id.avatarImageView);
        Glide.with(this).load(httpsImgUrl).into(myImg);

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

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
