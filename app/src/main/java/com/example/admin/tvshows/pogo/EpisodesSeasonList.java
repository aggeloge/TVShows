package com.example.admin.tvshows.pogo;

import com.example.admin.tvshows.EpisodesBySeason.ShowImageEpisode;

public class EpisodesSeasonList {

    private String summary;
    private String name;
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    ShowImageEpisode image;

    public ShowImageEpisode getImage() {
        return image;
    }

    public void setImage(ShowImageEpisode image) {
        this.image = image;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
