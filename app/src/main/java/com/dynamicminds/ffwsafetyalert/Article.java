package com.dynamicminds.ffwsafetyalert;

public class Article {
    private int id;
    private String title;
    private String image;
    private String body;

    public Article(int id, String title, String image, String body) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.body = body;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}