package com.webdev.project.backend.requests;

public class CreatePostRequest {
    private String title;
    private String body;
    private Boolean isPrivate;
    private String hashtags;

    public CreatePostRequest() {
    }

    public CreatePostRequest(String title, String body, Boolean isPrivate, String hashtags) {
        this.title = title;
        this.body = body;
        this.isPrivate = isPrivate;
        this.hashtags = hashtags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getHashtags() {
        return hashtags;
    }

    public void setHashtags(String hashtags) {
        this.hashtags = hashtags;
    }
}
