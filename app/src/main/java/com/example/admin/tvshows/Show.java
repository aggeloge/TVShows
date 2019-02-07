package com.example.admin.tvshows;

public class Show {

    private String name;
    private String type;
    private String summary;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    ShowRating rating;

    public ShowRating getRating() {
        return rating;
    }

    public void setRating(ShowRating rating) {
        this.rating = rating;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    ShowImage image;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ShowImage getImage() {
        return image;
    }

    public void setImage(ShowImage image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
