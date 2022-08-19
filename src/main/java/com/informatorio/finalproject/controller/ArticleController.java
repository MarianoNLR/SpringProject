package com.informatorio.finalproject.controller;


import com.informatorio.finalproject.converter.ArticleConverter;
import com.informatorio.finalproject.dto.ArticleDTO;
import com.informatorio.finalproject.entity.Article;
import com.informatorio.finalproject.repository.ArticleRepository;
import com.informatorio.finalproject.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RequestMapping("/api/v1")
@RestController
public class ArticleController {

    private ArticleRepository articleRepository;

    private final ArticleConverter articleConverter;

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, ArticleConverter articleConverter, ArticleService articleService) {
        this.articleRepository = articleRepository;
        this.articleConverter = articleConverter;
        //this.authorRepository = authorRepository;
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public ResponseEntity<?> getArticles(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "") String q){
        if (!q.isBlank() && q.length()<=3){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(articleService.getArticles(page, q), HttpStatus.OK);
    }

    @PostMapping("articles/add")
    public ResponseEntity<ArticleDTO>saveArticle(@Valid @RequestBody ArticleDTO articleDTO){
        Article article = articleService.createArticle(articleDTO);
        return new ResponseEntity<>(articleConverter.toDto(article), HttpStatus.CREATED);
    }

    @GetMapping("articles/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable Long id){
        Article article = articleService.getArticle(id);
        return new ResponseEntity<>(articleConverter.toDto(article), HttpStatus.OK);
    }

    @PutMapping("articles/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable Long id,@Valid @RequestBody ArticleDTO articleDTO){
        Article article = articleService.updateArticle(articleDTO, id);
        if (article != null){
            return new ResponseEntity<>(articleConverter.toDto(article), HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("articles/{id}")
    public ResponseEntity<?> patchArticle(@PathVariable Long id, @Valid @RequestBody ArticleDTO articleDTO){

        Article article = articleService.patchArticle(articleDTO, id);
        return new ResponseEntity<>(articleConverter.toDto(article), HttpStatus.OK);

    }

    @DeleteMapping("articles/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id){
        return new ResponseEntity<>(articleService.deleteArticle(id), HttpStatus.NO_CONTENT);
    }
}
