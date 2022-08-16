package com.informatorio.finalproject.dto;

import com.informatorio.finalproject.entity.Article;
import com.informatorio.finalproject.entity.Author;
import com.informatorio.finalproject.entity.Source;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArticleDTO {
    private long id;

    private String title;

    private String description;

    private String content;

    private String url;

    private String urlToImage;

    private LocalDate publishedAt;

    private String author;

    private List<Source> sources = new ArrayList<>();


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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    /*public Article toEntity(){
        Article article = new Article();;
        article.setId(this.getId());
        article.setTitle(this.getTitle());
        article.setContent(this.getContent());
        article.setDescription(this.getDescription());
        article.setUrl(this.getUrl());
        article.setUrlToImage(this.getUrlToImage());
        //Optional<Author> authorOptional = authorRepository.findById(article.getAuthor().getId());
        article.setAuthor(this.getAuthor());
        article.setSources(this.getSources());

        return article;
    }*/
}
