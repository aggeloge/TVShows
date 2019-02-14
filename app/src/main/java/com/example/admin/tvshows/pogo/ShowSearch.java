package com.example.admin.tvshows.pogo;

public class ShowSearch {

    private String name;
    ShowSearchImage image;
    ShowSearchRating rating;
    private String type;
    private String summary;
    private String id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ShowSearchRating getRating() {
        return rating;
    }

    public void setRating(ShowSearchRating rating) {
        this.rating = rating;
    }

    public ShowSearchImage getImage() {
        return image;
    }

    public void setImage(ShowSearchImage image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
