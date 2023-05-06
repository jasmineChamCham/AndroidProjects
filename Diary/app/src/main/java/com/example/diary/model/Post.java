package com.example.diary.model;


import java.util.Date;

public class Post {
    private String id;
    private String title;
    private String content;
    private String color;
    private String date;
    private String emotion;

    public Post(){

    }

    public Post(String id, String title, String content, String color, String date, String emotion) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.color = color;
        this.date = date;
        this.emotion = emotion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }
}
