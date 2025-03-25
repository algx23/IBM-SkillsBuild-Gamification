package com.example.lab6_s2_api.Domain;

public class Greeting {

    private Long id;
    private String content;
    private String language;

    public Greeting(long id, String content, String language) {
        this.id = id;
        this.content = content;
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
