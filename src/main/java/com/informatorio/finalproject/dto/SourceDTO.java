package com.informatorio.finalproject.dto;

import com.informatorio.finalproject.entity.Article;

import java.util.ArrayList;
import java.util.List;

public class SourceDTO {
    private long id;
    private String name;

    private String code;

    private String createdAt;


    public SourceDTO(long id, String name, String code, String createdAt) {
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


}
