package com.informatorio.finalproject.dto;

import com.informatorio.finalproject.entity.Article;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SourceDTO {
    private long id;
    private String name;

    private String code;

    private LocalDate createdAt;


    public SourceDTO(long id, String name, String code, LocalDate createdAt) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.createdAt = createdAt;
    }

    public SourceDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
