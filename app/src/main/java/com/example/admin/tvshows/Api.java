package com.example.admin.tvshows;

import com.example.admin.tvshows.Cast.CastList;
import com.example.admin.tvshows.pogo.EpisodesList;
import com.example.admin.tvshows.pogo.EpisodesSeasonList;
import com.example.admin.tvshows.pogo.PopularTvShows;
import com.example.admin.tvshows.pogo.SearchListShows;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL = "https://api.tvmaze.com/";

    @GET("schedule?")
    Call<List<PopularTvShows>> getShows(@Query("country") String country, @Query("date") String date);

    @GET("shows/{id}/seasons")
    Call<List<EpisodesList>> getEpisodes(@Path("id") String showID);

    @GET("seasons/{id}/episodes")
    Call<List<EpisodesSeasonList>> getEpisodesSeason(@Path("id") String showID);

    @GET("shows/{id}/cast")
    Call<List<CastList>> getCast(@Path("id") String showID);

    @GET("search/shows?")
    Call<List<SearchListShows>> getSearchShows(@Query("q") String q);
}
