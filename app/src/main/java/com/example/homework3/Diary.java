package com.example.homework3;

import com.fasterxml.jackson.annotation.JsonProperty;

class Diary {
    @JsonProperty("title") String title;
    @JsonProperty("content") String content;
    @JsonProperty("username") String username;

    public Diary(String title, String content, String username) {
        this.title = title;
        this.content = content;
        this.username = username;
    }
}
