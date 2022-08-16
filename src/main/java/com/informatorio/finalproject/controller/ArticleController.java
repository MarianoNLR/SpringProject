package com.informatorio.finalproject.controller;


import com.informatorio.finalproject.dto.ArticleDTO;
import com.informatorio.finalproject.entity.Article;
import com.informatorio.finalproject.repository.ArticleRepository;
import com.informatorio.finalproject.repository.AuthorRepository;
import com.informatorio.finalproject.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class ArticleController {

    private ArticleRepository articleRepository;

    private AuthorRepository authorRepository;

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, AuthorRepository authorRepository, ArticleService articleService) {
        this.articleRepository = articleRepository;
        //this.authorRepository = authorRepository;
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public List<ArticleDTO> getArticles(){
        return articleService.getArticles();
    }
/*
    @PostMapping
    public ResponseEntity<Article>saveArticle(@Validated @RequestBody Article article){
        Optional<Author> authorOptional = authorRepository.findById(article.getAuthor().getId());

        if(!authorOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        article.setAuthor(authorOptional.get());
        Article savedArticle = articleRepository.save(article);

        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedArticle.getId()).toUri();

        return ResponseEntity.created(ubicacion).body(savedArticle);
    }*/

    @GetMapping("articles/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id){
        Optional<Article> articleOptional = articleRepository.findById(id);
        //return ResponseEntity.ok(articleRepository.findById(id));
        return ResponseEntity.ok(articleOptional.get());
    }
}
