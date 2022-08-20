package com.informatorio.finalproject.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//A DTO just to show articles just with author's full name and not all properties
public class ArticleAuthorSimpleDTO {

    private long id;

    private String title;

    private String description;

    private String content;

    private String url;

    private String urlToImage;

    private LocalDate publishedAt;

    private String fullName;

    private List<SourceDTO> sources = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public LocalDate getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDate publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<SourceDTO> getSources() {
        return sources;
    }

    public void setSources(List<SourceDTO> sources) {
        this.sources = sources;
    }
}
