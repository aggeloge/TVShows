package com.example.admin.tvshows.pogo;

import com.example.admin.tvshows.Show;

public class PopularTvShows {

    private String name;
    private String season;
    private String number;

    public String getSeason() {
        return season;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    private Show show;

    public PopularTvShows(String name) {
        this.name = name;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}